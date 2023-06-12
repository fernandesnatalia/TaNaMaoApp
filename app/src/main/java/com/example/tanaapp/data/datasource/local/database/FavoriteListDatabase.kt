package com.example.tanaapp.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tanaapp.data.datasource.local.dao.FavoriteListDAO
import com.example.tanaapp.data.model.MenuItem

@Database(entities = [MenuItem::class],version = 1)
abstract class FavoriteListDatabase : RoomDatabase() {

    abstract fun favoriteListDAO(): FavoriteListDAO

}