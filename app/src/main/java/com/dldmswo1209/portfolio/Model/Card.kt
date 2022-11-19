package com.dldmswo1209.portfolio.Model

data class Card(
    var title: String,
    var content: String,
    var key: String,
    var image: String? = null, // 스토리지에서 가져올 이미지 uri
    var imageUri: String? = null, // 갤러리에서 가져온 이미지의 uri
    var link: String? = null,
    var start: String? = null,
    var end: String? = null,
){
    constructor(): this("","","")
}
