package com.dldmswo1209.portfolio.Model

data class User(
    val uid: String,
    var email: String,
    var password: String,
    var name: String,
    var isSuperUser: Boolean
)
