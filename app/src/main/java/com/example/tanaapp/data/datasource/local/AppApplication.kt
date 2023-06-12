package com.example.tanaapp.data.datasource.local


import android.app.Application
import androidx.room.Room
import com.example.tanaapp.data.datasource.local.database.FavoriteListDatabase

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            FavoriteListDatabase::class.java, "database-favlist"
        )
            .fallbackToDestructiveMigration().allowMainThreadQueries()
            .build()

    }

    companion object {
        private lateinit var database: FavoriteListDatabase
        fun getdatabase(): FavoriteListDatabase {
            return database
        }
    }

}