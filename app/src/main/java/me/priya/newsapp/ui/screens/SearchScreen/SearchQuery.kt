package me.priya.newsapp.ui.screens.SearchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.ShowLoading
import me.priya.newsapp.ui.base.UiState
import me.priya.newsapp.ui.screens.TopHeadlines.ArticleItem

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, end = 16.dp)
    ) {

        // 🔹 Search Bar
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.search(query) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val uiState = state) {
            is UiState.Success<List<Article>> -> {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(uiState.data) {
                        ArticleItem(it, onClick)
                    }
                }
            }


            is UiState.Loading -> ShowLoading()
            is UiState.Error -> Text("Error")
        }
    }
}