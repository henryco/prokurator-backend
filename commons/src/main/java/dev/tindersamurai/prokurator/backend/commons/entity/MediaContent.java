package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value @Builder @AllArgsConstructor
public class MediaContent {

	@Value @Builder @AllArgsConstructor
	public static class Channel {
		private String id;
		private String name;
		private String category;
		private boolean nsfw;
		private Details guild;
	}

	@Value @Builder @AllArgsConstructor
	public static class Details {
		private String id;
		private String name;
	}

	@Value @Builder @AllArgsConstructor
	public static class Media {
		private String id;
		private String url;
		private String name;
		private long size;
		private boolean image;
	}

	private String id;
	private long date;
	private boolean deleted;
	private Media media;
	private Details author;
	private Channel channel;

}
