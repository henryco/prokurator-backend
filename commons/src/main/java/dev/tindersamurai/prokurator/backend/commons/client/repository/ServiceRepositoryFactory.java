package dev.tindersamurai.prokurator.backend.commons.client.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import lombok.val;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ServiceRepositoryFactory {

	static <T> T create(@NonNull Class<T> repository, @NonNull String baseUrl) {
		val gson = new GsonBuilder().setLenient().create();
		return create(repository, GsonConverterFactory.create(gson), baseUrl);
	}

	static <T> T create(@NonNull Class<T> repository, @NonNull ObjectMapper objectMapper, @NonNull String baseUrl) {
		return create(repository, JacksonConverterFactory.create(objectMapper), baseUrl);
	}

	static <T> T create(@NonNull Class<T> repository, @NonNull Converter.Factory factory, @NonNull String baseUrl) {
		val log = Logger.getLogger(ServiceRepositoryFactory.class.getName());
		log.log(Level.CONFIG, "createClient: " + repository.getName());
		val retrofit = new Retrofit.Builder()
				.addConverterFactory(factory)
				.client(new OkHttpClient.Builder().build())
				.baseUrl(baseUrl)
				.build();
		return retrofit.create(repository);
	}
}
