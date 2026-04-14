package me.priya.newsapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.AndroidEntryPoint
import me.priya.newsapp.ui.navigation.AppNavGraph
import me.priya.newsapp.ui.theme.NewsAppTheme
import me.priya.newsapp.utils.WorkMangerHelper

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {

                AppNavGraph()
            }

            LaunchedEffect(Unit) {
                WorkMangerHelper.DeleteOldNewsWorker(this@MainActivity)
            }
        }


    }

}