package com.dldmswo1209.portfolio.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>().applicationContext
    private val repository = Repository(context)

    private var _cardList = MutableLiveData<List<CardEntity>>()
    val cardList : LiveData<List<CardEntity>>
        get() = _cardList

    private var _chatList = MutableLiveData<List<ChatEntity>>()
    val chatList : LiveData<List<ChatEntity>>
        get() = _chatList


    // 모든 카드 리스트를 가져올 것을 repository 에게 요청하는 메소드
    fun getAllCard() = viewModelScope.launch(Dispatchers.IO) {
        _cardList.postValue(repository.getAllCard())
    }
    // 모든 채팅 리스트를 가져올 것을 repository 에게 요청하는 메소드
    fun getAllChat() = viewModelScope.launch(Dispatchers.IO) {
        _chatList.postValue(repository.getAllChat())
    }

    // 카드를 추가하기 위해서 repository 에게 요청하는 메소드
    fun insertCard(card: CardEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCard(card)
    }

    // 채팅을 추가하기 위해서 repository 에게 요청하는 메소드
    fun insertChat(chat: ChatEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertChat(chat)
    }



}