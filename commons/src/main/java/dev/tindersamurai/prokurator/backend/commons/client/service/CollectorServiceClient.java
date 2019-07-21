package dev.tindersamurai.prokurator.backend.commons.client.service;

import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.CollectorServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;
import dev.tindersamurai.prokurator.backend.commons.service.ICollectorService;
import lombok.SneakyThrows;
import lombok.val;

public class CollectorServiceClient implements ICollectorService {

	private final CollectorServiceRepo serviceRepo;

	public CollectorServiceClient(CollectorServiceRepo serviceRepo) {
		this.serviceRepo = serviceRepo;
	}

	@Override @SneakyThrows
	public void saveDiscordEvent(CollectorEvent entity) {
		val response = serviceRepo.saveDiscordEvent(entity).execute();
		if (!response.isSuccessful()) {
			val error = response.errorBody();
			val e = error == null ? "" : error.string();
			throw new RuntimeException("Cannot store collector event: " + "[" + response.code() + "] " + e);
		}
	}
}
