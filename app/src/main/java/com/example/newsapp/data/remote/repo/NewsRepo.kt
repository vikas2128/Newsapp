package com.example.newsapp.data.remote.repo

import com.example.newsapp.data.remote.dto.news.NewsResponse


interface NewsRepo {
    suspend fun getNews(
        category: String,
        page: Int,
    ): NewsResponse

}