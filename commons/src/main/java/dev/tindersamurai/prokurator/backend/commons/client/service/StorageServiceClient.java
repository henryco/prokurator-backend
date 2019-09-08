package dev.tindersamurai.prokurator.backend.commons.client.service;

import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.StorageServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.service.IFileStorageService;
import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.val;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.tika.Tika;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Log
public class StorageServiceClient implements IFileStorageService {

	private final StorageServiceRepo serviceRepo;

	public StorageServiceClient(StorageServiceRepo serviceRepo) {
		this.serviceRepo = serviceRepo;
	}

	private ResponseBody callForFile(@NonNull String fid) throws FileNotFoundException {
		try {
			val response = serviceRepo.getFile(fid).execute();
			if (!response.isSuccessful()) {
				val error = response.errorBody();
				val e = error == null ? fid : (fid + ": " + error.string());
				throw new FileNotFoundException("[" + response.code() + "] " + e);
			}
			return response.body();
		} catch (IOException e) {
			log.throwing(StorageServiceClient.class.getName(), "getFile", e);
		}
		throw new FileNotFoundException(fid);
	}


	@Override
	public InputStream getFileStream(@NonNull String fid) throws FileNotFoundException {
		return callForFile(fid).byteStream();
	}

	@Override
	public byte[] getFileBytes(@NonNull String fid) throws FileNotFoundException {
		val file = callForFile(fid);
		try {
			return file.bytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public File getFile(@NonNull String fid) throws FileNotFoundException {
		return createTempFile(getFileStream(fid));
	}


	@Override
	public String getFileUrl(@NonNull String fid) {
		return serviceRepo.getFile(fid).request().url().toString();
	}

	@Override
	public String storeFile(@NonNull File file, String name) {
		log.info("storeFile: " + file);
		log.info("size: " + file.length());
		try {
			val type = MediaType.parse(new Tika().detect(file));
			val requestFile = RequestBody.create(type, file);

			// MultipartBody.Part is used to send also the actual file name
			val body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

			// add another part within the multipart request
			val fileName = name == null
					? RequestBody.create(okhttp3.MultipartBody.FORM, file.getName())
					: RequestBody.create(okhttp3.MultipartBody.FORM, name);

			val response = serviceRepo.uploadFile(fileName, body).execute();
			if (!response.isSuccessful() || response.body() == null) {
				val error = response.errorBody();
				val e = error == null ? "" : (": "  + error.string());
				log.warning("Unsuccessful request >storeFile<" + e);
				return null;
			}
			return response.body().getValue();
		} catch (IOException e) {
			throw new RuntimeException("Cannot store file", e);
		}
	}

	@Override
	public String storeFile(@NonNull InputStream stream, String name) {
		log.info("storeFile: " + stream + " name: " + name);
		val file = createTempFile(stream);
		val fid = storeFile(file, name);
		try {
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		} catch (Exception e) {
			log.throwing(StorageServiceClient.class.getName(), "storeFile", e);
			log.warning("Cannot delete file: " + e.getMessage());
			e.printStackTrace();
		}
		return fid;
	}

	private static File createTempFile(@NonNull InputStream stream) {
		log.info("createTempFile: " + stream);

		try {
			val path = Paths.get(".internal/tmp", UUID.randomUUID().toString());
			//noinspection ResultOfMethodCallIgnored
			new File(path.getParent().toUri()).mkdirs();

			val file = path.toFile();
			if (file.exists()) {
				val uniqueOne = UUID.randomUUID().toString() + "-" + file.getName();
				val uniquePath = Paths.get(path.getParent().toString(), uniqueOne);
				val uniqueFile = uniquePath.toFile();

				Files.copy(stream, uniquePath);
				uniqueFile.deleteOnExit();
				return uniqueFile;
			}

			Files.copy(stream, path);
			file.deleteOnExit();
			return file;
		} catch (IOException e) {
			throw new RuntimeException("Cannot create temp file", e);
		}
	}
}
