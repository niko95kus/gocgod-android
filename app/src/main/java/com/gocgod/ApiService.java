package com.gocgod;

import com.gocgod.model.ResponseSuccess;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Kevin on 8/4/2016.
 */
public interface ApiService {
    @GET("menu")
    Call<ResponseSuccess> getProduct(@QueryMap Map<String, String> options);

    @GET("menu_detail/{productId}")
    Call<ResponseSuccess> getProductDetail(@Path("productId") String productId);

    @GET("testimonial_data/{productId}")
    Call<ResponseSuccess> getProductTestimonial(@Path("productId") String productId, @QueryMap Map<String, String> options);
}
