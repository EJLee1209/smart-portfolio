package com.dldmswo1209.portfolio.Model

data class TimeLine(
    var content: String,
    var date: String,
    var subject: String,
    var key: String = "",
    var isSelected: Boolean = false,
    var year: String = ""
){
    constructor(): this("","","")

}