package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder @AllArgsConstructor
public class CollectorEvent {
	private @NonNull DiscordEntity user;
	private @NonNull ChannelEntity channel;

	@Value @Builder @AllArgsConstructor
	public static class DiscordEntity {
		private @NonNull String id;
		private @NonNull String name;
	}

	@Value @Builder @AllArgsConstructor
	public static class ChannelEntity {
		private @NonNull String id;
		private @NonNull String name;
		private @NonNull Boolean nsfw;
		private @NonNull DiscordEntity guild;
		private String category;
	}
}
