package me.priya.newsapp.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.priya.newsapp.data.repository.NewsRepository

@HiltWorker
class DeleteOldNewsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params : WorkerParameters,
    private val repository: NewsRepository,
) :  CoroutineWorker(context, params){

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {

        return try {

            repository.deleteOldNews(2)

            Result.Success()

        } catch(e : Exception){

            Result.retry()

        }

    }

}