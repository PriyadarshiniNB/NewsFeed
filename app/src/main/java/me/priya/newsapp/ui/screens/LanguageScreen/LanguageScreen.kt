package me.priya.newsapp.ui.screens.LanguageScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.ShowLoading
import me.priya.newsapp.ui.base.UiState
import me.priya.newsapp.ui.screens.TopHeadlines.ArticleItem

@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    Column(  modifier = Modifier
        .fillMaxSize()
        .padding(top = 50.dp, start = 16.dp, end = 16.dp)) {

        Button(onClick = {
            viewModel.fetchTwoLanguages("en", "fr")
        }) {
            Text("Loading English and french language")
        }

        when (val uistate = state) {
            is UiState.Success<List<Article>> -> {
                LazyColumn {
                    items(uistate.data) {
                        ArticleItem(it, onClick)
                    }
                }
            }

            is UiState.Loading ->   ShowLoading()
            is UiState.Error -> Text("Error")
        }
    }
}