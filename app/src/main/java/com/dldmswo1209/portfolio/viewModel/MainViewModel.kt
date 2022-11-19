package com.dldmswo1209.portfolio.viewModel

import android.net.Uri
import androidx.lifecycle.*
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

    fun getTimeLine(uid: String) : LiveData<MutableList<TimeLine>> {
        val timeLines = MutableLiveData<MutableList<TimeLine>>()
        repository.getTimeLine(uid).observeForever{
            timeLines.postValue(it)
        }

        return timeLines
    }
    fun updateProfile(user: User, imageUri: Uri?){
        repository.updateUser(user, imageUri)
    }

    fun updatePrivacyInfo(uid: String, profile: UserProfile){
        repository.updatePrivacyInfo(uid, profile)
    }

    fun createTimeLine(uid: String, timeLine: TimeLine){
        repository.createTimeLine(uid, timeLine)
    }


}