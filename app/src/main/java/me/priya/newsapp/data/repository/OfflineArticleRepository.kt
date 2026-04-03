package me.priya.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.priya.newsapp.data.api.NetworkService
import me.priya.newsapp.data.local.DatabaseService
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.model.toArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineArticleRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getArticles(country: String): Flow<List<Article>> {
        return flow { emit(networkService.getArticles(country)) }
            .map {
                it.apiArticles.map { apiArticle -> apiArticle.toArticleEntity() }
            }.flatMapConcat { articles ->
                flow { emit(databaseService.deleteAllAndInsertAll((articles))) }
            }.flatMapConcat {
                databaseService.getArticles()
            }
    }

    fun getArticlesDirectlyFromDB(): Flow<List<Article>> {
        return databaseService.getArticles()
    }

}