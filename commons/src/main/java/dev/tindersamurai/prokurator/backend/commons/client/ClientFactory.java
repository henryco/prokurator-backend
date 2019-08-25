package dev.tindersamurai.prokurator.backend.commons.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tindersamurai.prokurator.backend.commons.client.repository.ServiceRepositoryFactory;
import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.*;
import dev.tindersamurai.prokurator.backend.commons.client.service.*;
import dev.tindersamurai.prokurator.backend.commons.service.*;
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

	static IGuildService createGuildService(String baseUrl, ObjectMapper mapper) {
		val repo = ServiceRepositoryFactory.create(GuildServiceRepo.class, mapper, baseUrl);
		return new GuildServiceClient(repo);
	}
}
