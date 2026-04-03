package me.priya.newsapp.data.api

import me.priya.newsapp.data.model.ApiArticleResponse
import me.priya.newsapp.utils.AppConstant
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {

    @Headers("X-Api-Key: ${AppConstant.API_KEY}", "User-Agent: ABC")
    @GET("top-headlines")
    suspend fun getArticles(@Query("country") country: String): ApiArticleResponse

}