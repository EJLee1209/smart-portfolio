package com.dldmswo1209.portfolio.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.dldmswo1209.portfolio.entity.CardEntity
import com.dldmswo1209.portfolio.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>().applicationContext
    private val repository = Repository(context)

    private var _cardList = MutableLiveData<List<CardEntity>>()
    val cardList : LiveData<List<CardEntity>>
        get() = _cardList

    // 모든 카드 리스트를 가져올 것을 repository 에게 요청하는 메소드
    fun getAllCard() = viewModelScope.launch(Dispatchers.IO) {
        _cardList.postValue(repository.getAllCard())
    }

    // 카드를 추가하기 위해서 repository 에게 요청하는 메소드
    fun insertCard(card: CardEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCard(card)
    }

}