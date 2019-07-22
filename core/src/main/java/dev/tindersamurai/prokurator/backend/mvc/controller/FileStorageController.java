package dev.tindersamurai.prokurator.backend.mvc.controller;

import dev.tindersamurai.prokurator.backend.commons.entity.Response;
import dev.tindersamurai.prokurator.backend.mvc.service.storage.FileStorageService;
import dev.tindersamurai.prokurator.backend.commons.utils.MimeTypeMappings;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController @Slf4j
@RequestMapping("/api/storage")
public class FileStorageController {

	private final FileStorageService fileStorageService;

	@Autowired
	public FileStorageController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@PostMapping(
			value = "/upload",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	) public Response<String> upload(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name
	) throws IOException {
		log.debug("upload: {}, {}", file, name);
		val fileName = name == null ? file.getName() : name;
		val fid = fileStorageService.storeFile(file.getInputStream(), fileName);
		return new Response<>(fid);
	}

	@RequestMapping(
			method = {POST, GET},
			value = "/get/{fid}"
	) public ResponseEntity<Resource> getDocument(
			HttpServletRequest request,
			@PathVariable String fid
	) {
		log.debug("getDocument: {}, {}", request, fid);

		try {
			val fileStream = fileStorageService.getFileBytes(fid);

			val extension = MimeTypeMappings.getExtension(new Tika().detect(fileStream));
			val post = extension == null ? "" : ("." + extension);

			val headers = new HttpHeaders(); {
				headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + fid + post));
			}

			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(fileStream.length)
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new ByteArrayResource(fileStream));

		} catch (FileNotFoundException e) {
			log.error("Cannot locate file", e);
			return ResponseEntity.notFound().build();
		}
	}
}
