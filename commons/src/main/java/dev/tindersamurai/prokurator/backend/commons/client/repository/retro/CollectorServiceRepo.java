package dev.tindersamurai.prokurator.backend.commons.client.repository.retro;

import dev.tindersamurai.prokurator.backend.commons.entity.CollectorEvent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CollectorServiceRepo {

	@POST("api/collector/event")
	Call<Void> saveDiscordEvent(@Body CollectorEvent event);
}
