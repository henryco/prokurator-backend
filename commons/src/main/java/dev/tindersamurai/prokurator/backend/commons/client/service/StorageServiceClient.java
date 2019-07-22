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

	private  ResponseBody callForFile(@NonNull String fid) throws FileNotFoundException {
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
		return createTempFile(getFileStream(fid), fid);
	}

	@Override
	public String storeFile(@NonNull File file) {
		log.info("storeFile: " + file);
		try {
			val type = MediaType.parse(new Tika().detect(file));
			val requestFile = RequestBody.create(type, file);

			// MultipartBody.Part is used to send also the actual file name
			val body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

			// add another part within the multipart request
			val name = RequestBody.create(okhttp3.MultipartBody.FORM, file.getName());

			val response = serviceRepo.uploadFile(name, body).execute();
			if (!response.isSuccessful() || response.body() == null) {
				val error = response.errorBody();
				val e = error == null ? "" : (": "  + error.string());
				log.warning("Unsuccessful request >storeFile<" + e);
				return null;
			}
			return response.body().getValue();
		} catch (IOException e) {
			log.throwing(StorageServiceClient.class.getName(), "storeFile", e);
			return null;
		}
	}

	@Override
	public String storeFile(@NonNull InputStream stream, String name) {
		val file = createTempFile(stream, name);
		val fid = storeFile(file);
		try {
			if (file != null) {
				//noinspection ResultOfMethodCallIgnored
				file.delete();
			}
		} catch (Exception e) {
			log.throwing(StorageServiceClient.class.getName(), "storeFile", e);
		}
		return fid;
	}

	private static File createTempFile(@NonNull InputStream stream, String name) {
		try {
			val n = (name == null || name.isEmpty()) ? UUID.randomUUID().toString() : name;
			val file = File.createTempFile("", n);
			val path = Paths.get(file.toURI());
			if (file.exists()) {
				val uniqueOne = UUID.randomUUID().toString() + "-" + file.getName();
				val uniquePath = Paths.get(path.getParent().toString(), uniqueOne);
				Files.copy(stream, uniquePath);
				return file;
			}
			Files.copy(stream, path);
			return file;
		} catch (IOException e) {
			log.throwing(StorageServiceClient.class.getName(), "createTempFile", e);
			return null;
		}
	}
}
