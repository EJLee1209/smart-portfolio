package com.dldmswo1209.portfolio.Model

import java.io.Serializable

data class User(
    val uid: String,
    var email: String,
    var password: String,
    var name: String,
    var isSuperUser: Boolean,
    var profile: UserProfile? = null,
    val token: String = ""
): Serializable{ // 객체 직렬화 : 인텐트로 객체를 넘기기 위해서 직렬화를 해줘야 함
    constructor() : this("","","","",false)
}
