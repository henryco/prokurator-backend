package dev.tindersamurai.prokurator.backend.mvc.service.media.db;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.jpa.MediaPostRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.jpa.TextChannelRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.dao.jpa.UserRepo;
import dev.tindersamurai.prokurator.backend.mvc.data.entity.post.MediaPost;
import dev.tindersamurai.prokurator.backend.mvc.service.media.MediaService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
			mediaPost.setAuthor(author);
			mediaPost.setId(m.getId());
		}

		mediaPostRepo.save(mediaPost);
	}

}
