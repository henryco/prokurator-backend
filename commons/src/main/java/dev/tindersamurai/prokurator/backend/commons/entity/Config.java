package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value @Builder @AllArgsConstructor
public class Config {
	private @NotNull String name;
	private @NotNull Class<?> type;
	private Object value;
}
