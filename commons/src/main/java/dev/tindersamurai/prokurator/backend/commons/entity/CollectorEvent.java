package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value @Builder @AllArgsConstructor
public class CollectorEvent {
	private @NotNull DiscordEntity user;
	private @NotNull ChannelEntity channel;

	@Value @Builder @AllArgsConstructor
	public static class DiscordEntity {
		private @NotBlank String id;
		private @NotBlank String name;
	}

	@Value @Builder @AllArgsConstructor
	public static class ChannelEntity {
		private @NotBlank String id;
		private @NotBlank String name;
		private @NotNull Boolean nsfw;
		private @NotNull DiscordEntity guild;
		private String category;
	}
}
