package me.priya.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.priya.newsapp.data.local.dao.ArticleDao
import me.priya.newsapp.data.local.entity.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Hilt will call this, and ContentProvider can also call this
        fun getInstance(context: Context, name: String = "news_database"): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
    abstract fun articleDao(): ArticleDao
}