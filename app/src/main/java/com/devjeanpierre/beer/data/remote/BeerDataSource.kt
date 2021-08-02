package com.devjeanpierre.beer.data.remote

import com.devjeanpierre.beer.data.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BeerDataSource @Inject constructor(private val api: BeerApiClient){
    suspend fun getListBeer():List<Beer> {
        return withContext(Dispatchers.IO) {
            val response= api.getBeers()
            response.body() ?: emptyList()
        }
    }
}