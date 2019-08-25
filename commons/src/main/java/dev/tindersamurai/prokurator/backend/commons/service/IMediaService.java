package dev.tindersamurai.prokurator.backend.commons.service;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;

public interface IMediaService {
	void storeMedia(MediaEvent m);

	void removeMedia(String id);


}
