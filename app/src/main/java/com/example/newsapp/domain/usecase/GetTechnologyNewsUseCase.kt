package com.example.newsapp.domain.usecase


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.remote.dto.news.Article
import com.example.newsapp.data.remote.repo.NewsRepo
import javax.inject.Inject

class GetTechnologyNewsUseCase @Inject constructor(
    private val repo: NewsRepo
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPage = params.key ?: 1
            val response = repo.getNews("technology", nextPage)
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
        return 0;
    }
}