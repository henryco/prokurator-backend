package dev.tindersamurai.prokurator.backend.commons.client.repository.retro;

import dev.tindersamurai.prokurator.backend.commons.entity.Config;
import dev.tindersamurai.prokurator.backend.commons.entity.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConfigServiceRepo {

	@POST("api/configuration/add")
	Call<Response> addConfiguration(@Body Config config);

	@POST("api/configuration/set/{name}/{value}")
	Call<Response> setConfiguration(@Path("name") String name, @Path("value") String value);

	@DELETE("api/configuration/remove/{name}")
	Call<Response> removeConfiguration(@Path("name") String name);

	@POST("api/configuration/get/{name}")
	Call<Response<String>> getConfiguration(@Path("name") String name);

	@POST("api/configuration/exists/{name}")
	Call<Response<String>> isConfigurationExists(@Path("name") String name);
}
