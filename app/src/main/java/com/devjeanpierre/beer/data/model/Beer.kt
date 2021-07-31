package com.devjeanpierre.beer.data.model

data class Beer (
    val id:Int =0,
    val name:String="",
    val image_url:String="",
    val description:String="",
    val method:Method?= null
    )

data class Method(
    val  mash_temp:Mashtemp?= null,
    val fermentation:Fermentation?= null,
)

data class Mashtemp(
    val  temp:Temp?= null,
    val  duration:Int=0
)

data class Temp(
    val  value:Int=0,
    val  unit:String?= null
)


data class Fermentation(
    val  temp:Temp?= null,
    val  duration:Int=0
)

data class ResponseDataBeer(
    val beer: Beer ?= null,
    val messageError:String ?=null,
    val listBeer:List<Beer> ?=null
)