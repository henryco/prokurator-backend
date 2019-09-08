package dev.tindersamurai.prokurator.backend.commons.service;

import lombok.NonNull;

import java.io.*;

public interface IFileStorageService {

	InputStream getFileStream(@NonNull String fid) throws FileNotFoundException;

	byte[] getFileBytes(@NonNull String fid) throws FileNotFoundException;

	File getFile(@NonNull String fid) throws FileNotFoundException;

	String getFileUrl(@NonNull String fid);

	/** @return UID string */
	String storeFile(@NonNull File file, String name);

	/** @return UID string */
	String storeFile(@NonNull InputStream stream, String name);

	/** @return UID string */
	default String storeFile(@NonNull File file) {
		return storeFile(file, null);
	}

	/** @return UID string */
	default String storeFile(@NonNull InputStream stream) {
		return storeFile(stream, null);
	}

	/** @return UID string */
	default String storeFile(@NonNull byte[] bytes, String name) {
		return storeFile(new ByteArrayInputStream(bytes), name);
	}

	/** @return UID string */
	default String storeFile(@NonNull byte[] bytes) {
		return storeFile(bytes, null);
	}
}
