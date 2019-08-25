package dev.tindersamurai.prokurator.backend.mvc.service.collector.db.cache;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent.DiscordEntity;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.UserRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.entity.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
public class CUserService implements CachedUserService {

	private final UserRepo repo;

	public CUserService(UserRepo repo) {
		this.repo = repo;
	}

	@Override @Transactional @Cacheable(
			value = "collector_u", key = "#cacheId"
	) public void collectUser(DiscordEntity entity, String cacheId) {
		log.debug("collectUser: {}, {}", entity, cacheId);

		val user = new User(); {
			user.setName(entity.getName());
			user.setId(entity.getId());
			repo.save(user);
		}
	}
}
