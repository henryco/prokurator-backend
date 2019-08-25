package dev.tindersamurai.prokurator.backend.mvc.api;

import dev.tindersamurai.prokurator.backend.mvc.service.guilds.GuildService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController @Slf4j @Api
@RequestMapping("/api/guilds")
public class GuildsController {

	private final GuildService service;

	@Autowired
	public GuildsController(GuildService service) {
		this.service = service;
	}

	@PostMapping("/filter")
	public String[] filterBotGuilds(@RequestBody String[] ids) {
		log.debug("filterBotGuilds: {}", lazyToString(ids));
		return service.filterHandledGuilds(ids);
	}

	private static Object lazyToString(Object[] arr) {
		return new Object() {
			@Override
			public String toString() {
				return Arrays.toString(arr);
			}
		};
	}

}
