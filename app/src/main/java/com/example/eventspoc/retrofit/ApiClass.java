package com.example.eventspoc.retrofit;

import android.content.Context;
import android.util.Log;

import com.example.eventspoc.utility.util;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClass {


    public static final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/";

    private static Retrofit retrofit = null;
    int cacheSize = 10 * 1024 * 1024;

    public static Retrofit getClient(Context context) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()

//        httpClient.connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS);

//        if (retrofit == null) {


                .addInterceptor(logging)
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .cache(provideCache(context))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

//        }

        return retrofit;
    }

    private static Interceptor provideOfflineCacheInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (Exception e) {


                    CacheControl cacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    Request offlineRequest = chain.request().newBuilder()
                            .cacheControl(cacheControl)
                            .removeHeader("pragma")
                            .build();
                    return chain.proceed(offlineRequest);
                }
            }
        };
    }


    private static Interceptor provideCacheInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
//                    String cacheControl = originalResponse.header("Cache-Control");

//                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
//                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {


//                    CacheControl cc = new CacheControl.Builder()
//                            .maxStale(1, TimeUnit.DAYS)
//                            .build();

                int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
                    request = request.newBuilder()
                            //.cacheControl(cc)
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("pragma")
                            .build();

//                    return chain.proceed(request);

//                } else {
                    return originalResponse;
//                }
            }
        };

    }

    static Cache provideCache(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "offlineCache");

        Cache cache = null;
        try {
            cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("Cache", "Could not create Cache!");
        }

        return cache;
    }
}
