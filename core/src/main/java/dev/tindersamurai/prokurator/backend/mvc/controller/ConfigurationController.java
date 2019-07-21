package dev.tindersamurai.prokurator.backend.mvc.controller;

import dev.tindersamurai.prokurator.backend.commons.entity.Config;
import dev.tindersamurai.prokurator.backend.commons.entity.Response;
import dev.tindersamurai.prokurator.backend.mvc.service.configuration.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController @Slf4j
@RequestMapping("/api/configuration")
public class ConfigurationController {

	private final ConfigurationService configurationService;

	@Autowired
	public ConfigurationController(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@PostMapping("/add")
	public void addConfiguration(@RequestBody Config config) {
		log.debug("addConfiguration: {}", config);
		configurationService.addConfigParam(config.getName(), config.getType(), config.getValue());
	}

	@PostMapping("/set/{name}/{value}")
	public void setConfiguration(@PathVariable String name, @PathVariable String value) {
		log.debug("setConfiguration: {}, {}", name, value);
		configurationService.set(name, value);
	}

	@DeleteMapping("/remove/{name}")
	public void removeConfiguration(@PathVariable String name) {
		log.debug("removeConfiguration: {}", name);
		configurationService.removeConfigParam(name);
	}

	@PostMapping("/get/{name}")
	public Response<Config> getConfiguration(@PathVariable String name) {
		log.debug("getConfiguration: {}", name);
		val o = configurationService.get(name);
		if (o == null) return new Response<>(new Config(name, String.class, null));
		return new Response<>(new Config(name, o.getClass(), o));
	}

	@PostMapping("/exists/{name}")
	public Response<Boolean> isConfigurationExists(@PathVariable String name) {
		log.debug("isConfigurationExists: {}", name);
		return new Response<>(configurationService.isPropExists(name));
	}
}
