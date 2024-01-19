package com.example.newsapp.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {
    protected val _loading: MutableLiveData<Boolean> = MutableLiveData();
    val loading: LiveData<Boolean>
        get() = _loading;

    private val _messageState = MutableLiveData<Event<UIMessage>>()

    val messageState: LiveData<Event<UIMessage>>
        get() = _messageState

    open suspend fun messageHandler(message: UIMessage) {
        withContext(Dispatchers.Main) {
            _messageState.value = Event(message)
        }
    }

}
