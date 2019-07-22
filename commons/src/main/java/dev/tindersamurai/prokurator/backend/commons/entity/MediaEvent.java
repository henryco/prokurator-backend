package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value @Builder
@AllArgsConstructor
public class MediaEvent {

	@Value @Builder @AllArgsConstructor
	public static class Attachment {
		private boolean image;
		private long created;
		private int size;
		private String name;
		private String id;
	}

	private Attachment attachment;
	private String channelId;
	private String authorId;
	private String id;
}
