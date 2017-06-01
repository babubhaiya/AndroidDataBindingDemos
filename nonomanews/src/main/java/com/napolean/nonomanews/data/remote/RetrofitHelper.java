package com.napolean.nonomanews.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final long CONNECT_TIME_OUT = 60L;
    private static final long READ_TIME_OUT = 60L;

    public static NonomaNewsService newHackerNewsService() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);

        // Adding support for headers
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                final Request original = chain.request();
                final Request.Builder requestBuilder = original.newBuilder();

                requestBuilder.addHeader("accept", "application/json");
                requestBuilder.addHeader("Content-Type", "application/json; charset=utf-8");

                return chain.proceed(requestBuilder.build());
            }
        });


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        // Retrofit instance
        Retrofit retrofit = getRetrofitInitializer(NonomaNewsService.ENDPOINT, builder.build());

        return retrofit.create(NonomaNewsService.class);
    }

    /**
     * Generates Retrofit instance to be used to make Http request to get hacker news stories
     *
     * @param iBaseUrl base url for the hacker news store
     * @param iHttpClient underlying {@link OkHttpClient} instance
     * @return Retrofit instance
     */
    private static Retrofit getRetrofitInitializer(String iBaseUrl, OkHttpClient iHttpClient) {
        final Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(iBaseUrl)
                .client(iHttpClient)
                .build();
    }
}
