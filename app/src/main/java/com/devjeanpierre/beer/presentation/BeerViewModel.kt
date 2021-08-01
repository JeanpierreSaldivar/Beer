package com.devjeanpierre.beer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import com.devjeanpierre.beer.core.Result
import com.devjeanpierre.beer.domain.BeerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(private val repo: BeerImpl) : ViewModel() {

    fun getListBeer() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getListBeer()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}
