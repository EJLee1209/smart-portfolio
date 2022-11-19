package com.dldmswo1209.portfolio.Model

data class TimeLine(
    var content: String,
    var date: String,
    var key: String = ""
){
    constructor(): this("","","")

}
