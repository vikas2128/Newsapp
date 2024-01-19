package com.example.newsapp.common

import okhttp3.Interceptor
import okhttp3.Response

class HeaderRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("x-api-key", "df92dc7b4f834901886327ad00c7826d").build()
        return chain.proceed(request)
    }
}