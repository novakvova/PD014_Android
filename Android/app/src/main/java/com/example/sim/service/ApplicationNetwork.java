package com.example.sim.service;

import com.example.sim.constants.Urls;
import com.example.sim.network.AccountApi;
import com.example.sim.network.CategoriesApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationNetwork {
    private static ApplicationNetwork mInstance;
    private static final String BASE_URL = Urls.BASE;
    private Retrofit mRetrofit;

    private ApplicationNetwork() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static ApplicationNetwork getInstance() {
        if(mInstance==null)
            mInstance=new ApplicationNetwork();
        return mInstance;
    }
    public CategoriesApi getJsonApi() {
        return mRetrofit.create(CategoriesApi.class);
    }

    public AccountApi getAccountApi() {
        return mRetrofit.create(AccountApi.class);
    }
}
