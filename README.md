[예상 산출물 프로토타입](https://ovenapp.io/view/llTtDQBich8oWVQasgAksGQNBdnbzqJk)

<img src="https://user-images.githubusercontent.com/101651909/204493042-006e1daf-5847-49fd-b581-34944f5b486a.png" width="20%"/>

# Smart-Portfolio
<img src="https://user-images.githubusercontent.com/101651909/202912194-587b2d14-a521-4afe-9cd9-ab3adb979964.jpg" width="60%"/>
증명사진이 없어서 우주 최강 미남 강동원님 사진좀 빌려서 썼습니다,,,^^
# 앱 주요 기능

## 📌 Splash 화면
<img src="https://user-images.githubusercontent.com/101651909/202912283-34544297-d3f6-42b6-9ddc-919485cdc56d.jpg" width="60%"/>
로딩 애니메이션으로 lottie 애니메이션을 적용했습니다. <br>
1~3초 중 랜덤한 시간으로 로딩시간이 적용됩니다. <br>
로딩이 끝나면 오른쪽 화면이 나옵니다. <br>
첫 초기 화면은 github.io(깃허브 이력서) 를 webView 로 보여주도록 구현했습니다. <br>

## 📌 회원가입(채용 담당자 또는 일반 사용자)/로그인 
<img src="https://user-images.githubusercontent.com/101651909/202912201-a652ea18-265b-48a8-b856-f910bd5f8ed0.jpg" width="60%"/>
사용 목적에 따라 채용 담당자 또는 일반 사용자를 선택할 수 있습니다. <br>
채용 담당자로 회원가입 할 경우 관리자 코드가 필요합니다. <br>

## 📌 타임라인 포트폴리오
<img src="https://user-images.githubusercontent.com/101651909/205433691-d8260378-392b-4d71-bd93-e8416a63d33a.jpg" width="60%"/>

타임라인 형태의 포트폴리오 입니다. <br>
하단의 Floating 버튼을 클릭해서 타임라인을 추가할 수 있습니다. <br>

<img src="https://user-images.githubusercontent.com/101651909/205433701-a517e32c-8822-4b8d-853f-8b49abd80389.jpg" width="60%"/>
타임라인을 선택하여 수정 또는 삭제가 가능합니다.

## 📌 채팅 포트폴리오
<img src="https://user-images.githubusercontent.com/101651909/202912396-255670ec-8a78-4ce2-b0fd-6a629b71980e.jpg" width="60%"/>
채팅 형태의 포트폴리오 입니다. <br>
왼쪽 하단의 버튼을 클릭해서 일반모드/관리자 모드를 변경할 수 있고, 관리자 모드 상태에서는 수정 또는 삭제가 가능합니다 <br>

## 📌 카드 포트폴리오
<img src="https://user-images.githubusercontent.com/101651909/202912401-06ca5e33-da61-4a4c-9981-80df856a311c.jpg" width="60%"/>
<img src="https://user-images.githubusercontent.com/101651909/202912417-717154fc-b4af-46d7-b7c9-7d9acc298233.jpg" width="60%"/>
카드 형태의 포트폴리오 입니다. <br>
사진, 내용, 시작/끝 날짜, 링크를 저장할 수 있습니다. <br>
수정 또는 삭제가 가능합니다. <br>

 ## 📌프로필 사진 및 개인 정보 저장 기능
 <img src="https://user-images.githubusercontent.com/101651909/202912470-f5e2bc97-f932-45b7-b70f-6e4b454c7543.jpg" width="30%"/>
 
프로필 사진과 개인 정보를 수정할 수 있는 마이페이지 입니다. <br>
채용담당자에게 개성넘치는 프로필로 어필할 수 있습니다. <br>

## 📌 포트폴리오 열람 기능(채용담당자 권한)
<img src="https://user-images.githubusercontent.com/101651909/202912435-7928ec2a-0bde-4f71-9e9c-50377071f85a.jpg" width="60%"/>
채용 담당자가 로그인 했을 때 보여지는 화면입니다 <br>
모든 일반 사용자(채용인)들의 프로필 정보를 확인할 수 있습니다. <br>
채팅, 전화, 프로필 열람이 가능합니다. <br>

## 📌 채용담당자와 일반 사용자(채용인) 간 메세지 기능
<img src="https://user-images.githubusercontent.com/101651909/202912441-4ee15bd4-743e-4f2d-b6fa-c26cfad80642.jpg" width="60%"/>
채용인은 채용 담당자에게 먼저 메세지를 보낼 수 없고, 채용 담당자가 채용인에게 메세지를 보내면서 대화를 시작할 수 있습니다.
채용 담당자에게 메세지가 오면, 채팅방이 생성되고 채팅방 목록을 한 눈에 확인 할 수 있습니다.

## 📌 채팅 푸시 알림
<img src="https://user-images.githubusercontent.com/101651909/204470231-c131ea21-38a2-4dab-a499-a92f6186804c.jpg" width="60%"/>
왼쪽은 포그라운드 상태, 오른쪽은 백그라운드 상태에서의 푸시 알림 수신입니다.
포그라운드(앱이 실행 중), 백그라운드(다른앱을 실행 중 이거나 앱을 끈 상태) 모두 알림 수신이 가능하도록 구현했습니다.

# 🛠 사용 기술 및 라이브러리
- Android Studio(Kotlin)
- Visual Studio Code(Node.js)
- Firebase Realtime Database
- Firebase Authentication 
- Firebase Storage
- Firebase Cloud Messaging
- Google Cloud Platform Compute Engine
- Glide
- Coroutine
- Retrofit2
- Lottie

### [푸시메세지 서버 코드](https://github.com/EJLee1209/fcm-server)
