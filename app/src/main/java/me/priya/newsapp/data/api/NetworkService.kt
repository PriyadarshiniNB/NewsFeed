package me.priya.newsapp.data.api

import me.priya.newsapp.data.model.ApiArticleResponse
import me.priya.newsapp.data.model.ApiSourceResponse
import me.priya.newsapp.utils.AppConstant
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String
    ): ApiArticleResponse

    @GET("top-headlines/sources")
    suspend fun getSources(): ApiSourceResponse

    @GET("top-headlines")
    suspend fun getNewsBySource(
        @Query("sources") source: String
    ): ApiArticleResponse

    @GET("top-headlines")
    suspend fun getNewsByCountry(
        @Query("country") country: String
    ): ApiArticleResponse

    @GET("top-headlines")
    suspend fun getNewsByLanguage(
        @Query("language") language: String
    ): ApiArticleResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String
    ): ApiArticleResponse


}