package me.priya.newsapp.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("DELETE FROM article")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAllAndInsertAll(articles: List<Article>) {
        deleteAll()
        insertAll(articles)
    }

    @Query("DELETE FROM article WHERE created_at < :time")
    suspend fun deleteOldArticles(time: Long)

    @Query("SELECT * FROM article")
    fun getArticlesCursor(): Cursor

}