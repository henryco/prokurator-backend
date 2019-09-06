package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value @Builder @AllArgsConstructor
public class MediaContent {
	private String id;
	private long date;
	private long size;
	private boolean removed;
	private boolean image;
	private boolean nsfw;
	private String author;
	private String channel;
	private String file;
	private String name;
	private String category;
	private String guild;
}
