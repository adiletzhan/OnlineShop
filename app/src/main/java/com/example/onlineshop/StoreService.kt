package com.example.onlineshop

import retrofit2.Response
import retrofit2.http.GET

interface StoreService {
    @GET("/products")
    suspend fun getProducts(): Response<StoreProducts>
}