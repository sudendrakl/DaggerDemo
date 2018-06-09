package com.blackbeard.dagger.demo.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import jsonexclusion.test.in.jsonexclusion.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sudendra on 2/6/18.
 */

public class ApiServiceBuilder {
    private static String BASE_URL = "https://api.myjson.com/";

    private static OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        ChuckInterceptor chuckInterceptor = new ChuckInterceptor(context);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(chuckInterceptor);
        }
        return builder.build();
    }

    public static RestApi provideAPIService(Context context) {

        OkHttpClient okHttpClient = provideOkHttpClient(context);

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(HSTypeAdapterFactory.create())
                .create();
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RestApi.class);
    }
}
