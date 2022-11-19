package com.dldmswo1209.portfolio.Model



data class Chat(
    var content: String,
    var type: Int,
    var key: String = ""
){
    constructor() : this("",0,"")
}

// type(일반모드와 관리자 모드를 구분하기 위한 플래그)
const val MY_CHAT = 1
const val OTHER_CHAT = 0