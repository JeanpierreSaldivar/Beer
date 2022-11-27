package com.devjeanpierre.beer.data.remote

import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.data.remote.prueva.Data
import com.devjeanpierre.beer.data.remote.prueva.ShiftOnSiteResponse
import com.devjeanpierre.beer.data.remote.prueva.TestApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerDataSource @Inject constructor(/*private val api: BeerApiClient,*/
    private val apiTesst : TestApiClient){
   /* suspend fun getListBeer():List<Beer> {
        return withContext(Dispatchers.IO) {
            val response= api.getBeers()
            response.body() ?: emptyList()
        }
    }*/
    suspend fun getListDate():ShiftOnSiteResponse{
        return withContext(Dispatchers.IO) {
            val response= apiTesst.getList()
            response.body()!!
        }
    }
}