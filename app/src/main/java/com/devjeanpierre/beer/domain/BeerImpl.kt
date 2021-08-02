package com.devjeanpierre.beer.domain

import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.data.remote.BeerDataSource
import javax.inject.Inject

class BeerImpl @Inject constructor(private val dataSource: BeerDataSource){
    suspend fun getListBeer(): List<Beer> = dataSource.getListBeer()
}