package com.example.tanaapp.domain.repository

import com.example.tanaapp.data.datasource.local.dao.FavoriteListDAO
import com.example.tanaapp.data.datasource.remote.RetrofitService
import com.example.tanaapp.data.model.MenuItem

class Repository(private val dao: FavoriteListDAO){

    suspend fun getMenu():List<MenuItem>{
        return RetrofitService.getAPI().getMenu()
    }

    fun insertIntoDatabase(item:MenuItem) = dao.insertIntoDatabase(item)

    fun getFavoritedList():List<MenuItem> = dao.getFavoritedList()

    fun deleteFromDatabase(item: MenuItem) = dao.deleteFromDatabase(item.name)

    fun insertToCart(item:MenuItem) = dao.insertIntoCart(item)

    fun updateCartList(item:MenuItem)= dao.updateCartList(item)

    fun getCartList():List<MenuItem> = dao.getCartList()
}