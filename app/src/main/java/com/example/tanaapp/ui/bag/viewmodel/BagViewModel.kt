package com.example.tanaapp.ui.bag.viewmodel

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

class BagViewModel(): ViewModel(){
    private val useCase = UseCase()
    val bagState = SingleLiveEvent<ViewState<List<MenuItem>>>()

    fun getBagList(){
        viewModelScope.launch {
            bagState.value = ViewState.loading(null)
            try{
                val response = withContext(Dispatchers.Default){
                    useCase.getCartList()
                }
                bagState.value = response
            }catch(e:Exception){
                bagState.value = ViewState.error(null,e.message)
            }
        }
    }

    class BagModelFactory(): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(BagViewModel::class.java)){
                return BagViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}