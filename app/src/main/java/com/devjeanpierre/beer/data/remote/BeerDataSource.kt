package com.devjeanpierre.beer.data.remote

import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.data.model.ResponseDataBeer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BeerDataSource @Inject constructor(private val api: BeerApiClient) {

    suspend fun getListBeer():List<Beer> {
        return withContext(Dispatchers.IO) {
            val response= api.getBeers()
            response.body() ?: emptyList()
        }
    }
}