package com.example.tanaapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tanaapp.data.model.MenuItem

@Dao
interface FavoriteListDAO {

    @Query("Select * From item")
    fun getFavoritedList(): List<MenuItem>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavList(item: MenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoDatabase(item: MenuItem)

    @Query("DELETE FROM item WHERE name = :name")
    fun deleteFromDatabase(name: String)

    @Query("Select * From item")
    fun getCartList(): List<MenuItem>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCartList(item: MenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoCart(item: MenuItem)
}