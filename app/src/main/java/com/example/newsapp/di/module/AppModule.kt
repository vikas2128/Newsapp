package com.example.newsapp.di.module

import android.app.Application
import com.example.newsapp.common.HeaderRequestInterceptor
import com.example.newsapp.data.remote.api.NewsApi
import com.example.newsapp.data.remote.repo.NewsRepo
import com.example.newsapp.domain.repo.NewsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun apiKeyHeaderInterceptor(): HeaderRequestInterceptor {
        return HeaderRequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        headerRequestInterceptor: HeaderRequestInterceptor,
    ): OkHttpClient {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(headerRequestInterceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(gsonConverterFactory)
    }

    @Provides
    @Singleton
    fun providesNewsApi(retrofit: Retrofit.Builder): NewsApi {
        return retrofit.build().create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsRepo(api: NewsApi): NewsRepo {
        return NewsRepository(api)
    }
}