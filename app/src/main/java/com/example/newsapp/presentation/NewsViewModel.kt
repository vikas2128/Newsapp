package com.example.newsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.common.BaseViewModel
import com.example.newsapp.common.Configurations
import com.example.newsapp.data.remote.dto.news.Article
import com.example.newsapp.domain.usecase.GetBusinessNewsUseCase
import com.example.newsapp.domain.usecase.GetEntertainmentNewsUseCase
import com.example.newsapp.domain.usecase.GetGeneralNewsUseCase
import com.example.newsapp.domain.usecase.GetScienceNewsUseCase
import com.example.newsapp.domain.usecase.GetHealthNewsUseCase
import com.example.newsapp.domain.usecase.GetSportsNewsUseCase
import com.example.newsapp.domain.usecase.GetTechnologyNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getBusinessNewsUseCase: GetBusinessNewsUseCase,
    private val getEntertainmentNewsUseCase: GetEntertainmentNewsUseCase,
    private val getGeneralNewsUseCase: GetGeneralNewsUseCase,
    private val getHealthNewsUseCase: GetHealthNewsUseCase,
    private val getScienceNewsUseCase: GetScienceNewsUseCase,
    private val getSportsNewsUseCase: GetSportsNewsUseCase,
    private val getTechnologyNewsUseCase: GetTechnologyNewsUseCase,
) : BaseViewModel() {
    val _tabPosition: MutableLiveData<Int?> = MutableLiveData();

    val tabPosition: LiveData<Int?>
        get() = _tabPosition;

    val businessNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getBusinessNewsUseCase
        }.flow.cachedIn(viewModelScope)

    val entertainmentNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getEntertainmentNewsUseCase
        }.flow.cachedIn(viewModelScope)

    val generalNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getGeneralNewsUseCase
        }.flow.cachedIn(viewModelScope)

    val healthNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getHealthNewsUseCase
        }.flow.cachedIn(viewModelScope)

    val scienceNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getScienceNewsUseCase
        }.flow.cachedIn(viewModelScope)

    val sportsNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getSportsNewsUseCase
        }.flow.cachedIn(viewModelScope)

    val technologyNews: Flow<PagingData<Article>> =
        Pager(PagingConfig(pageSize = Configurations.PAGE_SIZE)) {
            getTechnologyNewsUseCase
        }.flow.cachedIn(viewModelScope)
}