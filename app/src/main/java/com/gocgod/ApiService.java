package com.gocgod;

import com.gocgod.model.ResponseSuccess;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Kevin on 8/4/2016.
 */
public interface ApiService {
    @GET("menu")
    Call<ResponseSuccess> getProduct(@QueryMap Map<String, String> options);

    @GET("menu_detail/{productId}")
    Call<ResponseSuccess> getProductDetail(@Path("productId") String productId);

    @Headers("Content-Type: application/json")
    @GET("testimonial_data/{productId}")
    Call<ResponseSuccess> getProductTestimonial(@Path("productId") String productId, @QueryMap Map<String, String> options);

    @GET("findalocation")
    Call<ResponseSuccess> getAgentLocation(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseSuccess> postLogin(@Field("email_masuk") String email, @Field("password_masuk") String password);

    @GET("logout")
    Call<ResponseSuccess> logout(@Query("api_token") String apiKey);
}
