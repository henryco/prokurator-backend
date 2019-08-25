package dev.tindersamurai.prokurator.backend.mvc.service.media;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaContent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaProbe;
import dev.tindersamurai.prokurator.backend.commons.service.IMediaService;

import java.util.List;

public interface MediaService extends IMediaService {

	List<MediaContent> filterMedia(MediaProbe probe);
}
