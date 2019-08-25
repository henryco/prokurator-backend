package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("ALL")
@Value @Builder @AllArgsConstructor
public class MediaProbe {

	@Value @Builder @AllArgsConstructor
	public static class Query {
		private String[] category;
		private String[] channel;
		private Boolean[] deleted;
		private Boolean[] nsfw;
		private String[] file;
		private String[] user;

		private Long before;
		private Long after;

		@Size(max = 255)
		private String raw;
	}

	@Value @Builder @AllArgsConstructor
	public static class Page {
		private int page;
		private int size;
	}

	private Query query;

	@NotNull
	private Page page;
}
