package com.devjeanpierre.beer.data.remote.source

import com.devjeanpierre.beer.data.model.Beer
import com.devjeanpierre.beer.data.model.ResponseDataBeer
import com.devjeanpierre.beer.data.remote.api.BeerApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerDataSource {

    suspend fun getListBeer():ResponseDataBeer{
        return withContext(Dispatchers.IO) {
            var listBeer = listOf<Beer>()
            var messageError = ""
            val getAllData= BeerApiClient().processApiBeer()?.getBeers()
            getAllData?.enqueue(object : Callback<List<Beer>?> {
                override fun onResponse(call: Call<List<Beer>?>, response: Response<List<Beer>?>) {
                    if (response.isSuccessful){
                        listBeer = response.body() ?: emptyList()
                    }else{
                        messageError = response.message()
                    }
                }

                override fun onFailure(call: Call<List<Beer>?>, t: Throwable) {
                    messageError = t.message ?:"Ocurrio un error"
                }

            })
            ResponseDataBeer(null,messageError, listBeer)
        }
    }

    suspend fun getBeer(name: String): ResponseDataBeer {
        return withContext(Dispatchers.IO) {
            var beer = Beer()
            var messageError = ""
            val getData= BeerApiClient().processApiBeer()?.getBeerForName(name)
            getData?.enqueue(object : Callback<Beer?> {
                override fun onResponse(call: Call<Beer?>, response: Response<Beer?>) {
                    if (response.isSuccessful){
                        beer = response.body()?: Beer()
                    }else{
                        messageError = response.message()
                    }
                }

                override fun onFailure(call: Call<Beer?>, t: Throwable) {
                    messageError = t.message ?:"Ocurrio un error"
                }

            })
            ResponseDataBeer(beer,messageError, null)
        }
    }
}