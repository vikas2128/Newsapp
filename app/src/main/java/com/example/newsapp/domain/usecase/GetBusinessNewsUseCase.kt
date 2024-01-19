package com.example.newsapp.domain.usecase


import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.remote.dto.news.Article
import com.example.newsapp.data.remote.repo.NewsRepo
import javax.inject.Inject

class GetBusinessNewsUseCase @Inject constructor(
    private val repo: NewsRepo
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        Log.e("PagingSource::::: ", "")

        return try {
            val nextPage = params.key ?: 1
            val response = repo.getNews("business", nextPage)
            LoadResult.Page(
                data = response.articles,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        Log.e("PagingSource::::: ", "getRefreshKey")

        return state.pages.lastIndex;
    }
}