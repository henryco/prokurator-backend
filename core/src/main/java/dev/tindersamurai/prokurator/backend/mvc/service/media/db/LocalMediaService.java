package dev.tindersamurai.prokurator.backend.mvc.service.media.db;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaContent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaProbe;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.MediaPostRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.TextChannelRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.UserRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.entity.post.MediaPost;
import dev.tindersamurai.prokurator.backend.mvc.service.media.MediaService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service @Slf4j
public class LocalMediaService implements MediaService {

	private final TextChannelRepo textChannelRepo;
	private final MediaPostRepo mediaPostRepo;
	private final UserRepo userRepo;

	@Autowired
	public LocalMediaService(
			TextChannelRepo textChannelRepo,
			MediaPostRepo mediaPostRepo,
			UserRepo userRepo
	) {
		this.textChannelRepo = textChannelRepo;
		this.mediaPostRepo = mediaPostRepo;
		this.userRepo = userRepo;
	}

	@Override @Transactional
	public void storeMedia(MediaEvent m) {
		log.debug("storeMedia: {}", m);
		val channel = textChannelRepo.getOne(m.getChannelId());
		val author = userRepo.getOne(m.getAuthorId());
		val attachment = m.getAttachment();

		val mediaPost = new MediaPost(); {
			mediaPost.setDate(new Date(attachment.getCreated()));
			mediaPost.setImage(attachment.isImage());
			mediaPost.setName(attachment.getName());
			mediaPost.setSize(attachment.getSize());
			mediaPost.setFileId(attachment.getId());

			mediaPost.setChannel(channel);
			mediaPost.setRemoved(false);
			mediaPost.setAuthor(author);
			mediaPost.setId(m.getId());
		}

		mediaPostRepo.save(mediaPost);
	}

	@Override @Transactional
	public void removeMedia(String id) {
		log.debug("removeMedia: {}", id);
		val found = mediaPostRepo.findById(id);
		if (!found.isPresent()) {
			log.warn("Media post is not present in DB: " + id);
			return;
		}

		val one = found.get();
		one.setRemoved(true);

		mediaPostRepo.save(one);
	}

	@Override @Transactional
	public List<MediaContent> filterMedia(MediaProbe probe) {
		log.debug("filterMedia: {}", probe);
		// TODO FILTERING
		return Collections.emptyList();
	}
}
