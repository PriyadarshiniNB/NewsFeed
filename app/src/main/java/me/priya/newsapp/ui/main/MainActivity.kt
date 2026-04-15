package me.priya.newsapp.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import me.priya.newsapp.ui.navigation.AppNavGraph
import me.priya.newsapp.ui.theme.NewsAppTheme
import me.priya.newsapp.utils.NetworkReceiver
import me.priya.newsapp.utils.WorkMangerHelper

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("FCM_TOKEN", it)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }

        networkReceiver = NetworkReceiver()

        var filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)

        setContent {
            NewsAppTheme {

                AppNavGraph()
            }

            LaunchedEffect(Unit) {
                WorkMangerHelper.DeleteOldNewsWorker(this@MainActivity)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }

}