package com.devjeanpierre.beer.data.remote.api

import com.devjeanpierre.beer.data.model.Beer
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BeerApiInterface {

    @FormUrlEncoded
    @POST("?page=2&per_page=80")
    fun getBeers(): Call<List<Beer>>?

    /*@POST("identifyIdentityFromSamples")
    Call<UmanickResponse> sendFace(@Body UmanickRequest umanickRequest);*/
    @FormUrlEncoded
    @POST()
    fun getBeerForName(
        @Field("beer_name") beerName: String?
    ): Call<Beer?>?
}