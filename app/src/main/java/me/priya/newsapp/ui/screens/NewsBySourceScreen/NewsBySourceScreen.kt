package me.priya.newsapp.ui.screens.NewsBySourceScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import me.priya.newsapp.ui.base.ShowLoading
import me.priya.newsapp.ui.base.UiState
import me.priya.newsapp.ui.screens.TopHeadlines.ArticleItem

@Composable
fun NewsBySourceScreen(
    onArticleClick : (String) -> Unit,
    viewModel: NewsBySourceViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        when (val uiState = state) {

            is UiState.Loading -> {

                ShowLoading()
            }

            is UiState.Success -> {

                LazyColumn {
                    items(uiState.data) { article ->
                        ArticleItem(article, onArticleClick)

                    }
                }

            }

            is UiState.Error -> {

                val coroutineScope = rememberCoroutineScope()

                Column {
                    Text(text = uiState.message)
                    Button(onClick = {
                        coroutineScope.launch {
                            viewModel.fetch()
                        }
                    }) {
                        Text("Retry")
                    }
                }

            }

        }
    }
}