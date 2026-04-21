package me.priya.newsapp

import android.app.Application
import android.os.StrictMode
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import androidx.work.WorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication : Application() , Configuration.Provider{

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) { // Only for developers
            enableStrictMode()
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun enableStrictMode() {

        // Thread Policy (main thread issues)
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()   // detect everything
                .penaltyLog()  // show in logcat
                .build()
        )

        // VM Policy (memory issues)
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }




}