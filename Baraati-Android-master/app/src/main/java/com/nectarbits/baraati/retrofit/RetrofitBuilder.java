package com.nectarbits.baraati.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nectarbits.baraati.generalHelper.AppUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nectabits on 31/3/16.
 */
public class RetrofitBuilder {

    private static RetrofitBuilder retrofitBuilder = new RetrofitBuilder();


    private RetrofitBuilder() {

    }

    public static RetrofitBuilder getInstance() {
        return retrofitBuilder;
    }


    public ApiEndpointInterface getRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.MINUTES);
        httpClient.readTimeout(10,TimeUnit.MINUTES);
        httpClient.writeTimeout(10,TimeUnit.MINUTES);
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUtils.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiEndpointInterface api=retrofit.create(ApiEndpointInterface.class);

        return api;
    }



}


