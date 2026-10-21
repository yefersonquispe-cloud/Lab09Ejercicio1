package com.example.lab09ejercicio1.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 0): ProductResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductModel
}
