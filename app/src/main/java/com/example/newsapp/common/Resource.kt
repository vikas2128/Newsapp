package com.example.newsapp.common

sealed class Resource<T>(
    val data: T? = null,
    val uiMessage: UIMessage = UIMessage("", UIMessageType.NONE)
) {
    class Success<T>(data: T, uiMessage: UIMessage) : Resource<T>(data, uiMessage)
    class Error<T>(data: T? = null, uiMessage: UIMessage) : Resource<T>(data, uiMessage)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}