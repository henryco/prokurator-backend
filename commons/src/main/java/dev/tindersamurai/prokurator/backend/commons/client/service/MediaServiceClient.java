package dev.tindersamurai.prokurator.backend.commons.client.service;

import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.MediaServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.commons.service.IMediaService;
import lombok.SneakyThrows;
import lombok.val;

public class MediaServiceClient implements IMediaService {

	private final MediaServiceRepo serviceRepo;

	public MediaServiceClient(MediaServiceRepo serviceRepo) {
		this.serviceRepo = serviceRepo;
	}

	@Override @SneakyThrows
	public void storeMedia(MediaEvent m) {
		val response = serviceRepo.saveMediaEvent(m).execute();
		if (!response.isSuccessful()) {
			val error = response.errorBody();
			val e = error == null ? "" : error.string();
			throw new RuntimeException("Cannot store media event: " + "[" + response.code() + "] " + e);
		}
	}

	@Override @SneakyThrows
	public void removeMedia(String id) {
		val response = serviceRepo.removeMedia(id).execute();
		if (!response.isSuccessful()) {
			val error = response.errorBody();
			val e = error == null ? "" : error.string();
			throw new RuntimeException("Cannot remove media post: " + "[" + response.code() + "] " + e);
		}
	}
}
