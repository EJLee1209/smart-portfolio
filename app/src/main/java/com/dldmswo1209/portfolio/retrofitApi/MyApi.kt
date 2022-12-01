package com.dldmswo1209.portfolio.retrofitApi

import retrofit2.http.Body
import retrofit2.http.POST


interface MyApi {
    // 푸시 메세지 요청
    @POST("/push")
    suspend fun sendPushMessage(
        @Body pushBody: PushBody
    )
}

data class PushBody(
    val token: String, // 메세지를 받는 사람의 fcm 토큰
    val from: String, // 보내는 사람
    val message: String // 메세지
)