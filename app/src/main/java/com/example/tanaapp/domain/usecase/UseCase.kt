package com.example.tanaapp.domain.usecase

import com.example.tanaapp.data.datasource.local.AppApplication
import com.example.tanaapp.data.model.MenuItem
import com.example.tanaapp.domain.repository.Repository
import com.example.tanaapp.ui.viewstate.ViewState

class UseCase {
    private val dao = AppApplication.getdatabase().favoriteListDAO()
    private val repository = Repository(dao)

    suspend fun getMenu(): ViewState<List<MenuItem>> {
        return try {
            val response = repository.getMenu()
            ViewState.success(response)
        } catch (e: Exception) {

            ViewState.error(null, e.message)
        }
    }

    fun updateFavList(item: MenuItem): ViewState<MenuItem> {
        return try {
            if (item.isFavorite) {
                repository.insertIntoDatabase(item)
            } else {
                repository.deleteFromDatabase(item)
            }
            ViewState.success(item)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun getFavoritedList(): ViewState<List<MenuItem>> {
        return try {
            val list = repository.getFavoritedList()
            if (list.isEmpty()) {
                ViewState.empty(list)
            } else {
                ViewState.success(list)
            }
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun sendToCart(item: MenuItem): ViewState<MenuItem> {
        return try {
            repository.insertToCart(item)
            repository.updateCartList(item)
            ViewState.success(item)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }

    fun getCartList(): ViewState<List<MenuItem>> {
        return try {
            val list = repository.getCartList()
            ViewState.success(list)
        } catch (e: Exception) {
            ViewState.error(null, e.message)
        }
    }
}