package com.devjeanpierre.beer.data.model

import com.google.gson.annotations.SerializedName

data class Beer (
    @SerializedName("id")
    val id:Int =0,
    @SerializedName("name")
    var name:String="",
    @SerializedName("image_url")
    val image_url:String="",
    @SerializedName("description")
    val description:String="",
    @SerializedName("ph")
    val ph:Double ?= 0.0
   )


data class ResponseDataBeer(
    @SerializedName("beer")
    val beer: Beer ?= null,
    @SerializedName("messageError")
    val messageError:String ?=null,
    @SerializedName("listBeer")
    val listBeer:List<Beer> ?=null
)