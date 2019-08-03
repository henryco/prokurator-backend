package dev.tindersamurai.prokurator.backend.mvc.controller;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;
import dev.tindersamurai.prokurator.backend.mvc.service.collector.CollectorService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController @Slf4j @Api
@RequestMapping("/api/collector")
public class CollectorController {

	private final CollectorService collectorService;

	@Autowired
	public CollectorController(CollectorService collectorService) {
		this.collectorService = collectorService;
	}

	@PostMapping(value = "/event", consumes = APPLICATION_JSON_UTF8_VALUE)
	public void saveCollectorEvent(@RequestBody CollectorEvent event) {
		collectorService.saveDiscordEvent(event);
	}
}
