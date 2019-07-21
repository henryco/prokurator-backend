package dev.tindersamurai.prokurator.backend.mvc.controller;

import dev.tindersamurai.prokurator.backend.mvc.service.configuration.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @Slf4j
@RequestMapping("/api/configuration")
public class ConfigurationController {

	private final ConfigurationService configurationService;

	@Autowired
	public ConfigurationController(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
