package com.dldmswo1209.portfolio.Model

data class ChatRoom(
    val sender: User,
    val receiver: User,
    var lastMessage: String = "",
    var lastTime: String = "",
    val key : String = ""
){
    constructor(): this(User(),User())
}
