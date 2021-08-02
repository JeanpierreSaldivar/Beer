package com.devjeanpierre.beer.data.remote

import com.devjeanpierre.beer.data.model.Beer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface BeerApiClient {
    @GET("beers?page=2&per_page=80")
    suspend fun getBeers(): Response<List<Beer>>
}