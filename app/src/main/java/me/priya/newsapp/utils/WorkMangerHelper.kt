package me.priya.newsapp.utils

import android.content.Context
import android.icu.util.TimeUnit
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager


object WorkMangerHelper {

     fun DeleteOldNewsWorker(context : Context){

         var request = PeriodicWorkRequestBuilder<DeleteOldNewsWorker>(1, java.util.concurrent.TimeUnit.DAYS).build()

         WorkManager.getInstance(context).enqueueUniquePeriodicWork(
             "delete old news",
             ExistingPeriodicWorkPolicy.KEEP,
             request
         )
     }
 }