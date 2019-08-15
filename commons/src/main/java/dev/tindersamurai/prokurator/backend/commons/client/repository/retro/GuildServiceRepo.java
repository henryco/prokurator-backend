package dev.tindersamurai.prokurator.backend.commons.client.repository.retro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GuildServiceRepo {

	@POST("api/guilds/filter")
	Call<String[]> filterBotGuilds(@Body String[] guilds);
}
