package com.example.newsapp.common

data class UIMessage(var message: String, val type: UIMessageType)

enum class UIMessageType {
    TOAST, SNACKBAR, DIALOG, NONE
}
