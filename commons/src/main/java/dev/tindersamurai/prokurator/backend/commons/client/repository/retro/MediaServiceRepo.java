package dev.tindersamurai.prokurator.backend.commons.client.repository.retro;

import dev.tindersamurai.prokurator.backend.commons.entity.MediaEvent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MediaServiceRepo {

	@POST("api/media/event")
	Call<Void> saveMediaEvent(@Body MediaEvent event);
}
