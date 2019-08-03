package dev.tindersamurai.prokurator.backend.mvc.controller;

import dev.tindersamurai.prokurator.backend.commons.entity.Response;
import dev.tindersamurai.prokurator.backend.mvc.service.storage.FileStorageService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.http.MediaType.*;

@RestController @Slf4j @Api
@RequestMapping("/api/storage")
public class FileStorageController {

	private final FileStorageService fileStorageService;

	@Autowired
	public FileStorageController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@PostMapping(
			value = "/upload",
			consumes = MULTIPART_FORM_DATA_VALUE,
			produces = APPLICATION_JSON_UTF8_VALUE
	) public Response<String> upload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name
	) throws IOException {
		log.debug("upload: {}, {}", file, name);
		log.debug("SIZE: {}", file.getSize());

		val fileName = name == null ? file.getOriginalFilename() : name;
		val fid = fileStorageService.storeFile(file.getInputStream(), fileName);
		return new Response<>(fid);
	}

	@GetMapping(value = "/get/{fid}", produces = APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource getFile(@PathVariable("fid") String fid)
			throws FileNotFoundException {
		log.debug("getDocument: {}", fid);
		return new FileSystemResource(fileStorageService.getFile(fid));
	}
}
