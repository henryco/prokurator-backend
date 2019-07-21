package dev.tindersamurai.prokurator.backend.commons.client.service;

import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.ConfigServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.service.IConfigurationService;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
public class ConfigServiceClient implements IConfigurationService {

	private final ConfigServiceRepo serviceRepo;

	public ConfigServiceClient(ConfigServiceRepo serviceRepo) {
		this.serviceRepo = serviceRepo;
	}

	@Override
	public <T> void addConfigParam(@NonNull String name, @NonNull Class<? extends T> type, T value) {

	}

	@Override
	public void removeConfigParam(@NonNull String name) {

	}

	@Override
	public <T> void set(@NonNull String name, T value) {

	}

	@Override
	public <T> T get(@NonNull String name) {
		return null;
	}

	@Override
	public boolean isPropExists(@NonNull String name) {
		return false;
	}
}
