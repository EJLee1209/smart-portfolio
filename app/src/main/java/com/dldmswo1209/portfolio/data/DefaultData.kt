package com.dldmswo1209.portfolio.data

import android.net.Uri
import com.dldmswo1209.portfolio.R
import com.dldmswo1209.portfolio.entity.*

// 최초 앱 실행시 기본으로 넣어줄 데이터들 입니다.

fun imageResToUri(imageRes: Int) : Uri{
    return Uri.parse(
        "android.resource://com.dldmswo1209.portfolio/"
                + imageRes)
}

// 내 기본 프로필 이미지 Uri
val defaultImageUri = imageResToUri(R.drawable.my_profile_image)
val defaultCardImageUriGitHub = imageResToUri(R.drawable.github)
val defaultCardImageUriKBSC = imageResToUri(R.drawable.kbsc)
val defaultCardImageUriBlog = imageResToUri(R.drawable.velog)
val defaultCardImageUriPortfolio = imageResToUri(R.drawable.smart_portfolio)
val defaultCardImageUriHallym = imageResToUri(R.drawable.hallym)


const val DEFAULT_INTRO = "항상 새로운 것을 공부하고자 하는 열망이 있는 안드로이드 신입 개발자 이은재 입니다."
const val DEFAULT_PHONE = "010-5878-1209"
const val DEFAULT_NAME = "이은재"
const val DEFAULT_ADDRESS = "강원도 춘천시"
const val DEFAULT_EMAIL = "dldmswo1209@gmail.com"


val defaultCardList = mutableListOf(
    CardEntity(
        1
        ,defaultCardImageUriBlog.toString()
        ,"개인 공부 블로그"
        ,"블로그에 제가 공부한 내용을 정리해서 올리고 있습니다."
        ,"https://velog.io/@dldmswo1209"),

    CardEntity(
        2
        ,defaultCardImageUriGitHub.toString()
        ,"GitHub"
        ,"1일 1 커밋을 목표로 하고 있습니다"
        ,"https://github.com/EJLee1209"),

    CardEntity(
        3,
        defaultCardImageUriKBSC.toString(),
        "KBSC 소프트웨어 경진대회 참여(장려상)",
        "심리상담 챗봇 안드로이드 앱 개발",
        "https://github.com/EJLee1209/Chatbot",
        "2022-07-30",
        "2022-11-05"

    ),
    CardEntity(
        4
        , defaultCardImageUriHallym.toString()
        ,"교내 Github 이력서 해커톤 대회 참여(동상)"
        ,"교내 이력서 공모전에서 수상했습니다."
        ,
        start = "2022-11-03",
        end = "2022-11-03"
    ),
    CardEntity(
        5
        ,defaultCardImageUriHallym.toString()
        ,"교내 서공제 공모전 참여(은상)"
        ,"춘천 관광지를 소개해주는 앱 개발(1인 개발)"
        ,"https://github.com/EJLee1209/ChunCheonConquest",
        "2022-10-17",
        "2022-11-04"
    ),
    CardEntity(
        6
        ,defaultCardImageUriPortfolio.toString()
        ,"Smart Portfolio"
        ,"포트폴리오를 관리하기 위한 스마트 포트폴리오 앱\n 더존비즈온 기업 연계 프로젝트"
        ,"https://github.com/EJLee1209/smart-protfolio")
)

val defaultChatList = mutableListOf(
    ChatEntity(
        1,
        "이름",
        OTHER_CHAT
    ),
    ChatEntity(
        2,
        "이은재",
        MY_CHAT
    ),
    ChatEntity(
        3,
        "생년월일",
        OTHER_CHAT
    ),
    ChatEntity(
        4,
        "1999년 12월 9일",
        MY_CHAT
    ),
    ChatEntity(
        5,
        "자기소개",
        OTHER_CHAT
    ),
    ChatEntity(
        6,
        "안녕하세요. 저는 안드로이드 개발자 이은재 라고 합니다.",
        MY_CHAT
    ),
    ChatEntity(
        7,
        "취미",
        OTHER_CHAT
    ),
    ChatEntity(
        8,
        "제 취미는 헬스 입니다. 개발자는 장시간 동안 의자에 앉아 있어야 하는 직업으로 알고 있습니다. 그래서 저는 매일매일 자세 교정과 체력 증진을 위해 운동을 꾸준히 하고 있습니다.",
        MY_CHAT
    ),
    ChatEntity(
        9,
        "좌우명",
        OTHER_CHAT
    ),
    ChatEntity(
        10,
        "제 좌우명은 \"일상을 바꾸기 전에는 삶을 변화시킬 수 없다. 성공의 비밀은 자기 일상에 있다.\" 입니다. 저는 제 일상을 바꾸기 위해 운동을 시작했습니다. 운동을 하면서 체력도 늘고, 집중력도 높아질 수 있었습니다.",
        MY_CHAT
    )
)

//val timeLineList = mutableListOf<TimeLineEntity>(
//    TimeLineEntity(1,"강원고등학교 입학", "2015-03-01"),
//    TimeLineEntity(2,"한림대학교 입학", "2018-03-01"),
//    TimeLineEntity(3,"안드로이드 공부 시작", "2022-03-01"),
//    TimeLineEntity(4,"Github 이력서 해커톤 대회(동상)", "2022-11-03"),
//    TimeLineEntity(5,"교내 대회 서공제 공모전(은상)", "2022-11-04"),
//    TimeLineEntity(6,"KBSC 소프트웨어 경진대회(장려상)", "2022-11-05")
//)