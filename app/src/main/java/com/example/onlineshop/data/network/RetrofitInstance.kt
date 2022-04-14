package com.example.onlineshop.data.network

import com.example.onlineshop.domain.StoreService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://fakestoreapi.com"

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: StoreService = getRetrofit().create(StoreService::class.java)

}