package dev.tindersamurai.prokurator.backend.commons.client.service;

import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.ConfigServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.entity.Config;
import dev.tindersamurai.prokurator.backend.commons.service.IConfigurationService;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import lombok.val;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Log
public class ConfigServiceClient implements IConfigurationService {

	private final ConfigServiceRepo serviceRepo;

	public ConfigServiceClient(ConfigServiceRepo serviceRepo) {
		this.serviceRepo = serviceRepo;
	}

	private static void processError(Response response) throws IOException {
		if (!response.isSuccessful()) {
			val error = response.errorBody();
			val e = error == null ? "" : error.string();
			throw new RuntimeException("Cannot store collector event: " + "[" + response.code() + "] " + e);
		}
	}

	@Override @SneakyThrows
	public <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, T value) {
		val config = new Config(name, type, value);
		val response = serviceRepo.addConfiguration(config).execute();
		processError(response);
	}

	@Override @SneakyThrows
	public void removeConfigParam(@NonNull String name) {
		val response = serviceRepo.removeConfiguration(name).execute();
		processError(response);
	}

	@Override @SneakyThrows
	public <T> void set(@NonNull String name, T value) {
		val response = serviceRepo.setConfiguration(name, Objects.toString(value)).execute();
		processError(response);
	}

	@Override @SneakyThrows
	public <T> T get(@NonNull String name) {
		val response = serviceRepo.getConfiguration(name).execute();
		processError(response);
		val body = response.body();
		if (body == null) return null;
		val config = body.getValue();

		//noinspection unchecked
		return (T) config.getValue();
	}

	@Override @SneakyThrows
	public boolean isPropExists(@NonNull String name) {
		val response = serviceRepo.isConfigurationExists(name).execute();
		processError(response);
		val body = response.body();
		if (body == null) throw new NullPointerException("Response for this method cannot be null");
		return body.getValue();
	}
}
