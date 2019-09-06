package dev.tindersamurai.prokurator.backend.commons.service;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaContent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaProbe;

import java.util.List;

public interface IMediaService {

	List<MediaContent> filterMedia(MediaProbe probe, String guild);

	void storeMedia(MediaEvent m);

	void removeMedia(String id);

}
