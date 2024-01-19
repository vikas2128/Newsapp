package com.example.newsapp.domain.repo

import com.example.newsapp.common.Configurations
import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.data.remote.dto.news.NewsResponse
import com.example.newsapp.data.remote.repo.NewsRepo
import com.google.gson.JsonObject

class NewsRepository(private val api: NewsApi) : NewsRepo {
    override suspend fun getNews(
        category: String,
        page: Int,
    ): NewsResponse {
        return api.getNews(category, "in", page, Configurations.PAGE_SIZE)
    }
}