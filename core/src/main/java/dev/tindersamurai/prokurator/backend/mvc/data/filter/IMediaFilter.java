package dev.tindersamurai.prokurator.backend.mvc.data.filter;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaContent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaProbe;

import java.util.List;

public interface IMediaFilter {

	List<MediaContent> buildQuery(MediaProbe probe, String guildId);

}
