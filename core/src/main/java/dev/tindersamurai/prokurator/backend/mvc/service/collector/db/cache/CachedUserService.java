package dev.tindersamurai.prokurator.backend.mvc.service.collector.db.cache;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent.DiscordEntity;

public interface CachedUserService {

	void collectUser(DiscordEntity user, String cacheId);
}
