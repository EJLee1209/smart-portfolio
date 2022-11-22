package com.dldmswo1209.portfolio.retrofitApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val BASE_URL = "http://34.97.242.198:8080"

    val client = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance(): Retrofit{
        return client
    }
}