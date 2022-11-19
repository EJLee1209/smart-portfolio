package com.dldmswo1209.portfolio.Model

import android.net.Uri
import java.io.Serializable

data class UserProfile(
    var image: String? = "", // 스토리지에서 가져올 이미지 uri
    var imageUri: String? = null, // 갤러리에서 가져온 이미지의 uri
    var resumeUrl: String = "", // 깃허브 이력서 링크
    var introduce: String? = "", // 자기 소개
    var phone: String = "", // 전화번호
    var email: String = "", // 이메일
    var address: String = "" // 주소
): Serializable{
    constructor(): this("",null,"","","","","")
}
