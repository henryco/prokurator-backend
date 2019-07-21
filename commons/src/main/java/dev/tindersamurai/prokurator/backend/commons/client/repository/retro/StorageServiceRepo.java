package dev.tindersamurai.prokurator.backend.commons.client.repository.retro;

import dev.tindersamurai.prokurator.backend.commons.entity.Response;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface StorageServiceRepo {

	/**
	 * @param name name of file, might be null
	 * @param file file to send, cannot be null
	 * @return file ID
	 */
	@Multipart @POST("api/storage/upload")
	Call<Response<String>> uploadFile(
			@Part("name") RequestBody name,
			@Part MultipartBody.Part file
	);

	/**
	 * @param fileId cannot be null
	 * @return file
	 */
	@POST("/api/storage/get/{fid}")
	Call<ResponseBody> getFile(@Path("fid") String fileId);
}
