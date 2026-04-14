package me.priya.newsapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import androidx.work.WorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() , Configuration.Provider{

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()




}