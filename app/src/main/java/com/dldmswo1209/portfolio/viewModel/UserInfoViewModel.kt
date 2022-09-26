package com.dldmswo1209.portfolio.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.entity.UserEntity
import com.dldmswo1209.portfolio.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// 마이페이지에서 사용할 ViewModel
// 유저 정보에 대한 데이터 관리를 이 ViewModel 에서 합니다.
class UserInfoViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>().applicationContext
    private val repository = Repository(context)

    private var _userInfo = MutableLiveData<UserEntity>()
    val userInfo : LiveData<UserEntity>
        get() = _userInfo

    private var _allUser = MutableLiveData<List<UserEntity>>()
    val allUser : LiveData<List<UserEntity>>
        get() = _allUser

    fun insertUser(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        async {
            repository.insertUser(user)
        }.await()
        async(Dispatchers.IO) {
            _userInfo.postValue(user)
        }
    }

    fun updateUser(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        async {
            repository.updateUser(user)
        }.await()
        async(Dispatchers.IO) {
            _userInfo.postValue(user)
        }
    }

    fun getAllUser() = viewModelScope.launch(Dispatchers.IO) {
        _allUser.postValue(repository.getAllUser())
    }
}