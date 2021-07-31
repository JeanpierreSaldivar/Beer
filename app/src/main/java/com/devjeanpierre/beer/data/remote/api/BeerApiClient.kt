package com.devjeanpierre.beer.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BeerApiClient {
    private var retrofitApi: BeerApiInterface? = null

    fun processApiBeer(): BeerApiInterface? {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        if (retrofitApi == null) {
            val retrofit: Retrofit =
                Retrofit.Builder().baseUrl(ApiConstants.baseBeerApiUrl).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            retrofitApi =
                retrofit.create(
                    BeerApiInterface::class.java
                )
        }
        return retrofitApi
    }

}