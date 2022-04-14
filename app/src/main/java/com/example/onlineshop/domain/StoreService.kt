package com.example.onlineshop.domain

import com.example.onlineshop.data.models.Products
import retrofit2.Response
import retrofit2.http.GET

interface StoreService {

    @GET("/products")
    suspend fun getProducts(): Response<Products>
}