package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value @Builder @AllArgsConstructor
public class Config {
	private @NonNull String name;
	private @NonNull Class<?> type;
	private Object value;
}
