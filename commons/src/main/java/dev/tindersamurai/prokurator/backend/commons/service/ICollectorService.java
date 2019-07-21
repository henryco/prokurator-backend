package dev.tindersamurai.prokurator.backend.commons.service;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;

public interface ICollectorService {

	void saveDiscordEvent(CollectorEvent entity);
}
