package dev.tindersamurai.prokurator.backend.commons.service;

import lombok.NonNull;

public interface IConfigurationService {

	<T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, T value);

	default <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type) {
		this.addConfigParam(name, type, null);
	}

	void removeConfigParam(@NonNull String name);

	<T> void set(@NonNull String name, T value);

	default <T> T get(@NonNull String name, @NonNull T defaultValue) {
		T o = this.get(name);
		return o == null ? defaultValue : o;
	}

	<T> T get(@NonNull String name);

	boolean isPropExists(@NonNull String name);
}
