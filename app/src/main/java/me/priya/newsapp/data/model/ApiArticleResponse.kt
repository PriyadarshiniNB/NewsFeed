package me.priya.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiArticleResponse(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val apiArticles: List<ApiArticle> = ArrayList(),
)
