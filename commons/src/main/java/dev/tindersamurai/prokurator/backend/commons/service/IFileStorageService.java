package dev.tindersamurai.prokurator.backend.commons.service;

import lombok.NonNull;

import java.io.*;

public interface IFileStorageService {

	OutputStream getFileStream(@NonNull String fid) throws FileNotFoundException;

	byte[] getFileBytes(@NonNull String fid) throws FileNotFoundException;

	File getFile(@NonNull String fid) throws FileNotFoundException;

	/** @return UID string */
	String storeFile(@NonNull File file);

	/** @return UID string */
	String storeFile(@NonNull InputStream stream, String name);

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
