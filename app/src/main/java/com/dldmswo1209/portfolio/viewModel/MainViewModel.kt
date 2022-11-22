package com.dldmswo1209.portfolio.viewModel

import android.net.Uri
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.Model.*
import com.dldmswo1209.portfolio.repository.Repository
import com.dldmswo1209.portfolio.retrofitApi.PushBody
import kotlinx.coroutines.launch

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

    fun refreshChat(uid: String){
        repository.refreshChat(uid)
    }

    fun getCard(uid: String): LiveData<MutableList<Card>> {
        val cardList = MutableLiveData<MutableList<Card>>()
        repository.getCard(uid).observeForever{
            cardList.postValue(it)
        }

        return cardList
    }

    fun createCard(uid: String, card: Card){
        repository.createCard(uid, card)
    }

    fun deleteCard(uid: String, card: Card){
        repository.deleteCard(uid, card)
    }

    fun updateCard(uid: String, card: Card){
        repository.updateCard(uid, card)
    }

    fun createChatRoom(sender: User, receiver: User): LiveData<String>{
        val createdRoomKey = MutableLiveData<String>()
        repository.createChatRooms(sender, receiver).observeForever {
            createdRoomKey.postValue(it)
        }
        return createdRoomKey
    }
    fun getRoom(uid: String, key: String): LiveData<ChatRoom>{
        val room = MutableLiveData<ChatRoom>()
        repository.getRoom(uid, key).observeForever {
            room.postValue(it)
        }
        return room
    }

    fun getChatRooms(user: User): LiveData<MutableList<ChatRoom>>{
        val rooms = MutableLiveData<MutableList<ChatRoom>>()
        repository.getChatRooms(user).observeForever {
            rooms.postValue(it)
        }
        return rooms
    }

    fun getAllChat(key: String): LiveData<MutableList<RealChat>>{
        val chats = MutableLiveData<MutableList<RealChat>>()
        repository.getAllChat(key).observeForever {
            chats.postValue(it)
        }
        return chats
    }

    fun sendMessage(chat: RealChat, key: String){
        repository.sendMessage(chat, key)
    }

    fun sendPushMessage(pushBody: PushBody) = viewModelScope.launch {
        repository.sendPushMessage(pushBody)
    }

    fun registerToken(uid: String, token: String){
        repository.registerToken(uid, token)
    }


}