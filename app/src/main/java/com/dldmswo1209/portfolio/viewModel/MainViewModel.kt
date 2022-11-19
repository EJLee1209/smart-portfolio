package com.dldmswo1209.portfolio.viewModel

import android.net.Uri
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.Model.Chat
import com.dldmswo1209.portfolio.Model.TimeLine
import com.dldmswo1209.portfolio.Model.User
import com.dldmswo1209.portfolio.Model.UserProfile
import com.dldmswo1209.portfolio.repository.Repository

// 메인 액티비티에서 사용할 ViewModel
// 포트폴리오의 데이터 관리를 이 ViewModel 에서 합니다.
class MainViewModel(): ViewModel() {
    private val repository = Repository()

    fun getUser(uid: String) : LiveData<User>{
        val user = MutableLiveData<User>()
        repository.getUser(uid).observeForever{
            user.value = it
        }

        return user
    }


    fun updateProfile(user: User, imageUri: Uri?){
        repository.updateUser(user, imageUri)
    }

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
}