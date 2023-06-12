package com.example.tanaapp.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tanaapp.R
import com.example.tanaapp.data.datasource.local.AppApplication
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.domain.repository.Repository
import com.example.tanaapp.domain.singlelivevent.SingleLiveEvent
import com.example.tanaapp.domain.usecase.UseCase
import com.example.tanaapp.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel: ViewModel() {
    private val useCase = UseCase()
    val listState = SingleLiveEvent<ViewState<List<MenuItem>?>>()
    val favorite = SingleLiveEvent<ViewState<MenuItem>>()
    val cart = SingleLiveEvent<ViewState<MenuItem>>()
    val favoriteList = getFavoritedList()


    fun getFavoritedList(): List<MenuItem>? {
        val dao = AppApplication.getdatabase().favoriteListDAO()
        val repository = Repository(dao)
        return try {

            repository.getFavoritedList()

        } catch (e: Exception) {
            null
        }

    }

    fun updateFavoritedList(item: MenuItem) {
        viewModelScope.launch {
            try {
                val withContext = withContext(Dispatchers.Default) {
                    useCase.updateFavList(item)
                }
                favorite.value = withContext
            } catch (e: Exception) {
                listState.value = ViewState.error(null, "${R.string.fav_error}")
            }
        }
    }

    fun sendItemToBag(item: MenuItem) {
        viewModelScope.launch {
            try {
                val withContext = withContext(Dispatchers.Default) {
                    useCase.sendToCart(item)
                }
                cart.value = withContext
            } catch (e: Exception) {
                listState.value = ViewState.error(null, "${R.string.bag_error}")
            }
        }
    }

    class DetailModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}