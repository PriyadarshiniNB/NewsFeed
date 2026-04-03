package me.priya.newsapp.data.local

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article

class AppDatabaseService constructor(private val appDatabase: AppDatabase) : DatabaseService {

    override fun getArticles(): Flow<List<Article>> {
        return appDatabase.articleDao().getAll()
    }

    override fun deleteAllAndInsertAll(articles: List<Article>) {
        appDatabase.articleDao().deleteAllAndInsertAll(articles)
    }

}