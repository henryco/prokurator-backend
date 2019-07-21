package dev.tindersamurai.prokurator.backend.commons.entity;

import lombok.Value;

@Value public class Response<T> {
	private T value;
}
