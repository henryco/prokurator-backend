package dev.tindersamurai.prokurator.backend.mvc.service.guilds;

import dev.tindersamurai.prokurator.backend.commons.service.IGuildService;

import java.util.Arrays;

public interface GuildService extends IGuildService {
	String[] filterHandledGuilds(String... guilds);

	static Object lazyToString(Object[] arr) {
		return new Object() {
			@Override
			public String toString() {
				return Arrays.toString(arr);
			}
		};
	}
}
