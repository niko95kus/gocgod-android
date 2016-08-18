package com.gocgod;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kevin on 8/4/2016.
 */
public class ServiceGenerator{
        public static final String API_BASE_URL = Global.IP + "gocgod/public/";
        private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        private static Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        public static <S> S createService(Class<S> serviceClass) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.interceptors().clear();
            httpClient.interceptors().add(loggingInterceptor);

            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            Retrofit retrofit = builder.client(httpClient.build()).build();
            return retrofit.create(serviceClass);
        }
}
