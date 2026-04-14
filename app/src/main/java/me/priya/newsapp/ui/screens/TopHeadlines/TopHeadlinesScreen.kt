package me.priya.newsapp.ui.screens.TopHeadlines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.UiState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import me.priya.newsapp.ui.base.ShowLoading


@Composable
fun TopHeadLineScreen(
    viewModel: TopHeadLineViewModel = hiltViewModel(),
    onArticleClick : (String) -> Unit
){
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

@Composable
fun ArticleItem(  article: Article,
                  onClick: (String) -> Unit){

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { article.url.let { onClick(it) } }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Text(text = article.title)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = article.description ?: "No Description")
        }
    }


}


