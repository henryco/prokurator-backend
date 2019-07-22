package dev.tindersamurai.prokurator.backend.commons.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tindersamurai.prokurator.backend.commons.client.repository.ServiceRepositoryFactory;
import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.CollectorServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.ConfigServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.MediaServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.StorageServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.client.service.CollectorServiceClient;
import dev.tindersamurai.prokurator.backend.commons.client.service.ConfigServiceClient;
import dev.tindersamurai.prokurator.backend.commons.client.service.MediaServiceClient;
import dev.tindersamurai.prokurator.backend.commons.client.service.StorageServiceClient;
import dev.tindersamurai.prokurator.backend.commons.service.ICollectorService;
import dev.tindersamurai.prokurator.backend.commons.service.IConfigurationService;
import dev.tindersamurai.prokurator.backend.commons.service.IFileStorageService;
import dev.tindersamurai.prokurator.backend.commons.service.IMediaService;
import lombok.val;

public interface ClientFactory {

	static ICollectorService createCollectorClient(String baseUrl, ObjectMapper mapper) {
		val repo = ServiceRepositoryFactory.create(CollectorServiceRepo.class, mapper, baseUrl);
		return new CollectorServiceClient(repo);
	}

	static IConfigurationService createConfigurationClient(String baseUrl, ObjectMapper mapper) {
		val repo = ServiceRepositoryFactory.create(ConfigServiceRepo.class, mapper, baseUrl);
		return new ConfigServiceClient(repo);
	}

	static IFileStorageService createFileStorageClient(String baseUrl, ObjectMapper mapper) {
		val repo = ServiceRepositoryFactory.create(StorageServiceRepo.class, mapper, baseUrl);
		return new StorageServiceClient(repo);
	}

	static IMediaService createMediaClient(String baseUrl, ObjectMapper mapper) {
		val repo = ServiceRepositoryFactory.create(MediaServiceRepo.class, mapper, baseUrl);
		return new MediaServiceClient(repo);
	}
}
