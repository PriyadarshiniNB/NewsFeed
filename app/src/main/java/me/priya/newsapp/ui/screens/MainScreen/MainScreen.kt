package me.priya.newsapp.ui.screens.MainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(onNavigate:(String) -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        Button(onClick = { onNavigate("top_headlines")}) {
            Text(text = "Top HeadLines")
        }

        Button(onClick = { onNavigate("sources")}) {
            Text(text = "Sources")
        }

        Button(onClick = { onNavigate("search")}) {
            Text(text = "search")
        }

        Button(onClick = { onNavigate("countriesList")}) {
            Text(text = "Countries")
        }

        Button(onClick = { onNavigate("languageList")}) {
            Text(text = "Language")
        }

        Button(onClick = { onNavigate("offline")}) {
            Text(text = "offline")
        }

    }
}