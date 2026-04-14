package me.priya.newsapp.data.model

import com.google.gson.annotations.SerializedName
import me.priya.newsapp.data.local.entity.Article

data class ApiArticle(
    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("urlToImage")
    val imageUrl: String? = "",

    @SerializedName("source")
    val apiSource: ApiSource
)

fun ApiArticle.toArticleEntity(): Article {
    return Article(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = apiSource.toSourceEntity()
    )
}