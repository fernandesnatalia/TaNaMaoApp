package com.example.tanaapp.data.datasource.remote.model

import com.example.tanaapp.data.model.MenuItem
import retrofit2.http.GET

interface MenuResponse {
    @GET("menu")
    suspend fun getMenu(): List<MenuItem>
}