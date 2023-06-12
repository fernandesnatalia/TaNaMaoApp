package com.example.tanaapp.ui.favoritelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.domain.singlelivevent.SingleLiveEvent
import com.example.tanaapp.domain.usecase.UseCase
import com.example.tanaapp.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteListViewModel(): ViewModel() {
    private val useCase = UseCase()
    val favState = SingleLiveEvent<ViewState<List<MenuItem>>>()

    fun getFavoritedList() {
        viewModelScope.launch {
            favState.value = ViewState.loading(null)
            try {
                val response = withContext(Dispatchers.Default) {
                    useCase.getFavoritedList()
                }
                favState.value = response
            } catch (e: Exception) {
                favState.value = ViewState.error(null, e.message)
            }
        }
    }

    class FavoriteListViewModelFactory(): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(FavoriteListViewModel::class.java)){
                return FavoriteListViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}