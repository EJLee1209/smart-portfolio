package com.dldmswo1209.portfolio.retrofitApi

import retrofit2.http.Body
import retrofit2.http.POST


interface MyApi {
    @POST("/push")
    suspend fun sendPushMessage(
        @Body pushBody: PushBody
    )
}

data class PushBody(
    val token: String,
    val from: String,
    val message: String
)