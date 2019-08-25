package dev.tindersamurai.prokurator.backend.mvc.api;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.mvc.service.media.MediaService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController @Slf4j @Api
@RequestMapping("/api/media")
public class MediaController {

	private final MediaService mediaService;

	@Autowired
	public MediaController(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	@PostMapping(value = "/event", consumes = APPLICATION_JSON_UTF8_VALUE)
	public void saveMediaEvent(@RequestBody MediaEvent event) {
		log.debug("saveMediaEvent: {}", event);
		mediaService.storeMedia(event);
	}

	@DeleteMapping("/remove/{id}")
	public void removeMediaPost(@PathVariable String id) {
		log.debug("removeMediaPost: {}", id);
		mediaService.removeMedia(id);
	}
}
