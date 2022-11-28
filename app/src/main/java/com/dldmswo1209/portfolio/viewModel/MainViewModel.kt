package com.dldmswo1209.portfolio.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.Model.*
import com.dldmswo1209.portfolio.repository.Repository
import com.dldmswo1209.portfolio.retrofitApi.PushBody
import kotlinx.coroutines.launch
import java.net.ConnectException

// 메인 액티비티에서 사용할 ViewModel
// 포트폴리오의 데이터 관리를 이 ViewModel 에서 합니다.
class MainViewModel(): ViewModel() {
    private val repository = Repository()

    // 특정 사용자 데이터 가져오기
    fun getUser(uid: String) : LiveData<User>{
        val user = MutableLiveData<User>()
        repository.getUser(uid).observeForever{
            user.value = it
        }

        return user
    }

    // 모든 일반 사용자 정보 가져오기
    fun getAllUser(): LiveData<MutableList<User>>{
        val userList = MutableLiveData<MutableList<User>>()
        repository.getAllUser().observeForever{
            userList.value = it
        }

        return userList
    }

    // 프로필 정보 변경(이름, 소개, 사진)
    fun updateProfile(user: User, imageUri: Uri?){
        repository.updateUser(user, imageUri)
    }

    // 개인 정보 수정 (주소, 번호, 깃허브 이력서...)
    fun updatePrivacyInfo(uid: String, profile: UserProfile){
        repository.updatePrivacyInfo(uid, profile)
    }

    // 타임라인 가져오기
    fun getTimeLine(uid: String) : LiveData<MutableList<TimeLine>> {
        val timeLines = MutableLiveData<MutableList<TimeLine>>()
        repository.getTimeLine(uid).observeForever{
            timeLines.postValue(it)
        }

        return timeLines
    }

    // 타임라인 생성
    fun createTimeLine(uid: String, timeLine: TimeLine){
        repository.createTimeLine(uid, timeLine)
    }

    // 타임라인 삭제
    fun deleteTimeLine(uid: String, key: String){
        repository.deleteTimeLine(uid, key)
    }

    // 타임라인 수정
    fun updateTimeLine(uid: String, timeLine: TimeLine){
        repository.updateTimeLine(uid, timeLine)
    }

    // 채팅 가져오기
    fun getChat(uid: String) : LiveData<MutableList<Chat>> {
        val chatList = MutableLiveData<MutableList<Chat>>()
        repository.getChat(uid).observeForever{
            chatList.postValue(it)
        }

        return chatList
    }

    // 채팅 보내기
    fun sendChat(uid: String, chat: Chat){
        repository.sendChat(uid, chat)
    }
    // 채팅 삭제
    fun deleteChat(uid: String, key: String){
        repository.deleteChat(uid, key)
    }

    // 채팅 수정
    fun modifyChat(uid: String, chat: Chat){
        repository.modifyChat(uid, chat)
    }

    // 채팅 포트폴리오 새로고침
    // 임의로 빈 값을 넣고, 바로 삭제해서 새로고침 효과를 얻음
    fun refreshChat(uid: String){
        repository.refreshChat(uid)
    }

    // 카드 포트폴리오 가져오기
    fun getCard(uid: String): LiveData<MutableList<Card>> {
        val cardList = MutableLiveData<MutableList<Card>>()
        repository.getCard(uid).observeForever{
            cardList.postValue(it)
        }

        return cardList
    }

    // 카드 생성
    fun createCard(uid: String, card: Card){
        repository.createCard(uid, card)
    }

    // 카드 삭제
    fun deleteCard(uid: String, card: Card){
        repository.deleteCard(uid, card)
    }

    // 카드 수정
    fun updateCard(uid: String, card: Card){
        repository.updateCard(uid, card)
    }

    // 채팅방 생성
    fun createChatRoom(sender: User, receiver: User): LiveData<String>{
        val createdRoomKey = MutableLiveData<String>()
        repository.createChatRooms(sender, receiver).observeForever {
            createdRoomKey.postValue(it)
        }
        return createdRoomKey
    }
    // 특정 채팅방 가져오기
    fun getRoom(uid: String, key: String): LiveData<ChatRoom>{
        val room = MutableLiveData<ChatRoom>()
        repository.getRoom(uid, key).observeForever {
            room.postValue(it)
        }
        return room
    }

    // 나의 모든 채팅방 가져오기
    fun getChatRooms(user: User): LiveData<MutableList<ChatRoom>>{
        val rooms = MutableLiveData<MutableList<ChatRoom>>()
        repository.getChatRooms(user).observeForever {
            rooms.postValue(it)
        }
        return rooms
    }

    // 특정 채팅방의 모든 채팅 기록 가져오기
    fun getAllChat(key: String): LiveData<MutableList<RealChat>>{
        val chats = MutableLiveData<MutableList<RealChat>>()
        repository.getAllChat(key).observeForever {
            chats.postValue(it)
        }
        return chats
    }

    // 메세지 보내기
    fun sendMessage(chat: RealChat, key: String){
        repository.sendMessage(chat, key)
    }

    // 푸시 메세지 알림 보내기
    fun sendPushMessage(pushBody: PushBody) = viewModelScope.launch {
        try{
            repository.sendPushMessage(pushBody)
        }catch (e: ConnectException){
            e.printStackTrace()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    // FCM 토큰 등록
    fun registerToken(uid: String, token: String){
        repository.registerToken(uid, token)
    }


}