package dev.tindersamurai.prokurator.backend.commons.client.repository.retro;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaContent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import dev.tindersamurai.prokurator.backend.commons.entity.MediaProbe;
import dev.tindersamurai.prokurator.backend.commons.entity.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface MediaServiceRepo {

	@POST("api/media/event")
	Call<Void> saveMediaEvent(@Body MediaEvent event);

	@DELETE("api/media/remove/{id}")
	Call<Void> removeMedia(@Path("id") String id);

	@POST("api/filter/{gid}")
	Call<Response<List<MediaContent>>> filterMedia(
			@Body MediaProbe probe,
			@Path("gid") String guildId
	);
}
