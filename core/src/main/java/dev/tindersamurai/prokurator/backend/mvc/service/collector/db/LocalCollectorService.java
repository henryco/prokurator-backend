package dev.tindersamurai.prokurator.backend.mvc.service.collector.db;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;
import dev.tindersamurai.prokurator.backend.mvc.service.collector.CollectorService;
import dev.tindersamurai.prokurator.backend.mvc.service.collector.db.cache.CachedChannelService;
import dev.tindersamurai.prokurator.backend.mvc.service.collector.db.cache.CachedUserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class LocalCollectorService implements CollectorService {

	private final CachedChannelService channelService;
	private final CachedUserService userService;

	@Autowired
	public LocalCollectorService(
			CachedChannelService channelService,
			CachedUserService userService
	) {
		this.channelService = channelService;
		this.userService = userService;
	}

	@Override @Transactional
	public void saveDiscordEvent(CollectorEvent entity) {
		log.debug("saveDiscordEvent: {}", entity);

		val channel = entity.getChannel();
		val cid = channel.getGuild().getId() + "-" + channel.getId();
		channelService.collectChannel(channel, cid);

		val user = entity.getUser();
		userService.collectUser(user, user.getId());
	}
}
