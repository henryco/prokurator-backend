package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Value @Builder
@AllArgsConstructor
public class MediaEvent {

	@Value @Builder @AllArgsConstructor
	public static class Attachment {
		private boolean image;

		@PositiveOrZero
		private long created;

		@PositiveOrZero
		private int size;

		@NotBlank
		private String name;

		@NotBlank
		private String id;
	}

	@NotNull
	private Attachment attachment;

	@NotBlank
	private String channelId;

	@NotBlank
	private String authorId;

	@NotBlank
	private String id;
}
