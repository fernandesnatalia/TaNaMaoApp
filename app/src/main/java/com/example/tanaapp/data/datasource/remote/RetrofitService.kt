package com.example.tanaapp.data.datasource.remote

import com.example.tanaapp.data.datasource.remote.model.MenuResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitService {

    private const val URL_BASE = "https://tana-app.herokuapp.com/"

    fun getAPI(): MenuResponse {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(MenuResponse::class.java)
    }
}