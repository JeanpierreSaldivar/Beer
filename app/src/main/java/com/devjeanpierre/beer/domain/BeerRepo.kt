package com.devjeanpierre.beer.domain

import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.data.model.ResponseDataBeer

interface BeerRepo {
    suspend fun getListBeer(): ResponseDataBeer
    suspend fun getBeerForName(name:String): ResponseDataBeer
}