package me.priya.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import me.priya.newsapp.data.api.NetworkService
import me.priya.newsapp.data.local.DatabaseService
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.model.ApiArticle
import me.priya.newsapp.data.model.ApiSource
import me.priya.newsapp.data.model.toArticleEntity
import me.priya.newsapp.utils.NetworkHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val networkHelper: NetworkHelper
) {
    fun getTopHeadlines(country: String): Flow<List<Article>> = flow {

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = networkService.getTopHeadlines(country)

                val articles = response.apiArticles.map {
                    it.toArticleEntity()
                }

                databaseService.deleteAllAndInsertAll(articles)

                emit(articles)

            } catch (e: Exception) {
                emitAll(databaseService.getArticles())
            }

        } else {
            emitAll(databaseService.getArticles())
        }
    }

    fun getSources(): Flow<List<ApiSource>> = flow {
        val response = networkService.getSources()
        emit(response.sources)
    }

    fun getNewsBySource(source: String): Flow<List<Article>> = flow {

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = networkService.getNewsBySource(source)

                val articles = response.apiArticles.map {
                    it.toArticleEntity()
                }

                databaseService.deleteAllAndInsertAll(articles)

                emit(articles)

            } catch (e: Exception) {
                // fallback
            }
        }

        emitAll(databaseService.getArticles())
    }

    //  News by Country (Offline First)
    fun getNewsByCountry(country: String): Flow<List<Article>> = flow {

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = networkService.getNewsByCountry(country)

                val articles = response.apiArticles.map {
                    it.toArticleEntity()
                }

                databaseService.deleteAllAndInsertAll(articles)

            } catch (e: Exception) {
                // fallback
            }
        }

        emitAll(databaseService.getArticles())
    }

    //  News by Language (Offline First)
    fun getNewsByLanguage(language: String): Flow<List<Article>> = flow {

        if (networkHelper.isNetworkConnected()) {
            try {
                val response = networkService.getNewsByLanguage(language)

                val articles = response.apiArticles.map {
                    it.toArticleEntity()
                }

                databaseService.deleteAllAndInsertAll(articles)

            } catch (e: Exception) {
                // fallback
            }
        }

        emitAll(databaseService.getArticles())
    }

    //  Search (Online only)
    fun searchNews(query: String): Flow<List<Article>> = flow {
        val response = networkService.searchNews(query)
        emit(response.apiArticles.map { it.toArticleEntity() })
    }

    //  Two Languages (ZIP Operator)
    fun getNewsByTwoLanguages(
        lang1: String,
        lang2: String
    ): Flow<List<Article>> {

        val flow1 = flow {
            emit(networkService.getNewsByLanguage(lang1))
        }

        val flow2 = flow {
            emit(networkService.getNewsByLanguage(lang2))
        }

        return flow1.zip(flow2) { res1, res2 ->

            val list1 = res1.apiArticles.map { it.toArticleEntity() }
            val list2 = res2.apiArticles.map { it.toArticleEntity() }

            (list1 + list2).shuffled()
        }
    }

    //  Offline Only Screen
    fun getOfflineArticles(): Flow<List<Article>> {
        return databaseService.getArticles()
    }

    suspend fun deleteOldNews(days: Int) {
        val timeLimit = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L)
        databaseService.deleteOldArticles(timeLimit)
    }

}