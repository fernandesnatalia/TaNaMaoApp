package com.example.tanaapp.ui.menu.viewmodel

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

class MenuViewModel(): ViewModel(){
    private val useCase = UseCase()
    val menu = SingleLiveEvent<ViewState<List<MenuItem>>>()

    fun getMenu(){
        viewModelScope.launch {
            menu.value = ViewState.loading(null)
            try{
                val withContext = withContext(Dispatchers.Default){
                    useCase.getMenu()
                }
                withContext?.let {
                    menu.value = ViewState.success(it.data)
                }
            }catch(e:Exception){
                menu.value = ViewState.error(null,e.message)
            }
        }
    }

    class MenuViewModelFactory(): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MenuViewModel::class.java)){
                return MenuViewModel() as T
            }
            throw IllegalArgumentException("unknown viewmodel class")
        }
    }
}