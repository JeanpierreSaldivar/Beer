package com.devjeanpierre.beer.domain

import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.data.model.ResponseDataBeer
import com.devjeanpierre.beer.data.remote.api.BeerApiClient
import com.devjeanpierre.beer.data.remote.source.BeerDataSource

class BeerImpl(private val dataSource: BeerDataSource) : BeerRepo {
    override suspend fun getListBeer(): ResponseDataBeer = dataSource.getListBeer()

    override suspend fun getBeerForName(name:String): ResponseDataBeer = dataSource.getBeer(name)
}