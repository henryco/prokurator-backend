package dev.tindersamurai.prokurator.backend.commons.client.service;

import dev.tindersamurai.prokurator.backend.commons.client.repository.retro.GuildServiceRepo;
import dev.tindersamurai.prokurator.backend.commons.service.IGuildService;
import lombok.SneakyThrows;
import lombok.val;

public class GuildServiceClient implements IGuildService {

	private final GuildServiceRepo repo;

	public GuildServiceClient(GuildServiceRepo repo) {
		this.repo = repo;
	}

	@Override @SneakyThrows
	public String[] filterHandledGuilds(String... guilds) {
		val response = repo.filterBotGuilds(guilds).execute();
		if (!response.isSuccessful()) {
			val error = response.errorBody();
			val e = error == null ? "" : error.string();
			throw new RuntimeException("Cannot filter bot handled guilds: [" + response.code() + "] " + e);
		}
		return response.body();
	}
}
