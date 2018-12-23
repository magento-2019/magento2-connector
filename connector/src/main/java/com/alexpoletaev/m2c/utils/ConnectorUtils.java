package com.alexpoletaev.m2c.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectorUtils {
    public static final String DEFAULT_PERIOD = "day";
    public static final String DEFAULT_STORE_CODE = "all";
    public static final String DEFAULT_CONTENT_TYPE = "application/json";;

    public static Retrofit getRetrofitInstance(String siteUrl) {
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();

        return new Retrofit.Builder()
            .baseUrl(siteUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    }
}
