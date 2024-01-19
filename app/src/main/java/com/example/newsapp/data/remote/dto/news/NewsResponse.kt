package com.example.newsapp.data.remote.dto.news

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)