package dev.tindersamurai.prokurator.backend.mvc.service.collector.db.cache;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent.ChannelEntity;

public interface CachedChannelService {
	void collectChannel(ChannelEntity entity, String cacheId);
}
