package com.devjeanpierre.beer.data.remote.prueva

import com.devjeanpierre.beer.data.model.Beer
import retrofit2.Response
import retrofit2.http.GET

interface TestApiClient {
    @GET("services/appointment/get-consult/1234")
    suspend fun getList(): Response<ShiftOnSiteResponse>
}