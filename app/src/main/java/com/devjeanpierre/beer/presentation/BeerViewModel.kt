package com.devjeanpierre.beer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devjeanpierre.beer.domain.BeerRepo
import kotlinx.coroutines.Dispatchers
import com.devjeanpierre.beer.core.Result

class BeerViewModel(private val repo: BeerRepo) : ViewModel() {

    fun getListBeer() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getListBeer()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getBeer(name: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getBeerForName(name)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}