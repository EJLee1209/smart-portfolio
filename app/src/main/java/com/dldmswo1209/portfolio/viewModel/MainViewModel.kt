package com.dldmswo1209.portfolio.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.entity.ChatEntity
import com.dldmswo1209.portfolio.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    // 코루틴으로 작업이 진행되고 있는 상황에서 다른 화면으로 넘어간 경우에
    // 코루틴을 중단시키는 작업을 해줘야하는데, 그 작업을 도와주는게 바로 viewModelScope 이다.
    // 이녀석이 LifeCycle 을 인식해서 알아서 코루틴을 cancel 해준다
    fun getAllCard() = viewModelScope.launch(Dispatchers.IO) {
        // Dispatchers.IO 로 명시를 안해주면 메인 스레드에서는 DB 에 접근할 수 없다는 오류 메세지가 뜨면서 앱이 죽는다
        _cardList.postValue(repository.getAllCard())
    }
    // 카드를 추가하기 위해서 repository 에게 요청하는 메소드
    fun insertCard(card: CardEntity) = viewModelScope.launch(Dispatchers.IO) {
        // 카드 추가 -> getAllCard() 호출
        async {
            repository.insertCard(card)
        }.await() // await() 으로 데이터 삽입이 완료 될 때 까지 기다리고 다음 작업을 한다.
        async {
            getAllCard() // 데이터 삽입이 완료된 상태에서 모든 데이터를 가져온다.
        }
        // await 을 하지 않고 비동기적으로 실행한다면, 데이터가 삽입되지 않은 채 데이터를 가져오는 오류를 일으킬 수 있다.
    }
    // card 업데이트 메서드
    fun updateCard(card: CardEntity) = viewModelScope.launch(Dispatchers.IO){
        async {
            repository.updateCard(card)
        }.await()
        async {
            getAllCard()
        }
    }

    fun deleteCard(card: CardEntity) = viewModelScope.launch(Dispatchers.IO){
        async {
            repository.deleteCard(card)
        }.await()
        async {
            getAllCard()
        }
    }

    // 모든 채팅 리스트를 가져올 것을 repository 에게 요청하는 메소드
    fun getAllChat() = viewModelScope.launch(Dispatchers.IO) {
        _chatList.postValue(repository.getAllChat())
    }

    // 채팅을 추가하기 위해서 repository 에게 요청하는 메소드
    fun insertChat(chat: ChatEntity) = viewModelScope.launch(Dispatchers.IO) {
        async {
            repository.insertChat(chat)
        }.await()
        async {
            getAllChat()
        }
    }

    fun updateChat(chat: ChatEntity) = viewModelScope.launch(Dispatchers.IO) {
        async {
            repository.updateChat(chat)
        }.await()
        async {
            getAllChat()
        }
    }

    fun deleteChat(chat: ChatEntity) = viewModelScope.launch(Dispatchers.IO) {
        async {
            repository.deleteChat(chat)
        }.await()
        async {
            getAllChat()
        }
    }



}