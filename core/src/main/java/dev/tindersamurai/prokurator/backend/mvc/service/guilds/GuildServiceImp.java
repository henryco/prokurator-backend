package dev.tindersamurai.prokurator.backend.mvc.service.guilds;

import dev.tindersamurai.prokurator.backend.mvc.data.dao.GuildRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.entity.Guild;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static dev.tindersamurai.prokurator.backend.mvc.service.guilds.GuildService.*;

@Service @Slf4j
public class GuildServiceImp implements GuildService {

	private final GuildRepo repo;

	@Autowired
	public GuildServiceImp(GuildRepo repo) {
		this.repo = repo;
	}

	@Override @Transactional
	public String[] filterHandledGuilds(String... guilds) {
		log.debug("filterHandledGuilds: {}", lazyToString(guilds));
		@Cleanup val stream = repo.findAllByIdIn(Arrays.asList(guilds));
		return stream.map(Guild::getId).toArray(String[]::new);
	}

}
