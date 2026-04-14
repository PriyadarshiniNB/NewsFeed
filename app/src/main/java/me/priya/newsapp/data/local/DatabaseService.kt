package me.priya.newsapp.data.local

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article

interface DatabaseService {

    fun getArticles(): Flow<List<Article>>

    suspend fun deleteAllAndInsertAll(articles: List<Article>)

}