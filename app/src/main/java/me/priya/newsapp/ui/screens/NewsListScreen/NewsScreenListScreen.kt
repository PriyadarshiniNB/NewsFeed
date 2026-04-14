package me.priya.newsapp.ui.screens.NewsListScreen


import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.UiState
import me.priya.newsapp.ui.screens.TopHeadlines.ArticleItem
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import me.priya.newsapp.ui.base.ShowLoading

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    when (state) {
        is UiState.Success -> {
                val list = (state as UiState.Success<List<Article>>).data

                if (list.isEmpty()) {
                    Text("No articles found")
                } else {
                    LazyColumn {
                        items(list) {
                            ArticleItem(it, onClick)
                        }
                    }
                }
        }

        is UiState.Loading ->   ShowLoading()
        is UiState.Error -> Text("Error")
    }
}
