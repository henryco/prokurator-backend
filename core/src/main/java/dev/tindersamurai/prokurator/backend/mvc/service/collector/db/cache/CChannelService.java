package dev.tindersamurai.prokurator.backend.mvc.service.collector.db.cache;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent.ChannelEntity;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.GuildRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.TextChannelRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.entity.Guild;
import dev.tindersamurai.prokurator.backend.mvc.data.entity.TextChannel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class CChannelService implements CachedChannelService {

	private final TextChannelRepo textChannelRepo;
	private final GuildRepo guildRepo;

	@Autowired
	public CChannelService(
			TextChannelRepo textChannelRepo,
			GuildRepo guildRepo
	) {
		this.textChannelRepo = textChannelRepo;
		this.guildRepo = guildRepo;
	}

	@Override @Transactional @Cacheable(
			value = "collector_c", key = "cacheId"
	) public void collectChannel(ChannelEntity entity, String cacheId) {
		log.debug("collectChannel: {}, {}", entity, cacheId);

		val guild = new Guild(); {
			val _guild = entity.getGuild();
			guild.setName(_guild.getName());
			guild.setId(_guild.getId());
			guildRepo.save(guild);
		}

		val textChannel = new TextChannel(); {
			textChannel.setCategory(entity.getCategory());
			textChannel.setName(entity.getName());
			textChannel.setNsfw(entity.getNsfw());
			textChannel.setId(entity.getId());
			textChannel.setGuild(guild);
			textChannelRepo.save(textChannel);
		}
	}

}
