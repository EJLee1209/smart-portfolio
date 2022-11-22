package com.dldmswo1209.portfolio.Model

data class RealChat(
    var message: String, // 메세지
    var date_time: String, // 시간
    val sender: User, // 보낸 사람
    val receiver: User,// 받는 사람
    var key: String
){
    constructor(): this("","",User(),User(),"")
}
