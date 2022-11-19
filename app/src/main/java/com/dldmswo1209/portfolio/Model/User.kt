package com.dldmswo1209.portfolio.Model

import java.io.Serializable

data class User(
    val uid: String,
    var email: String,
    var password: String,
    var name: String,
    var isSuperUser: Boolean,
    var profile: UserProfile? = null
): Serializable{
    constructor() : this("","","","",false)
}
