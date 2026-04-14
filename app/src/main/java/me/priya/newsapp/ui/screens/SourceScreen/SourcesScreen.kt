package me.priya.newsapp.ui.screens.SourceScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.model.ApiSource
import me.priya.newsapp.ui.base.ShowLoading
import me.priya.newsapp.ui.base.UiState

@Composable
fun SourceScreen(
    viewModel: SourcesViewModel = hiltViewModel(),
    onSourceClick: (String) -> Unit
){
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    when(val uiState = state){
        is UiState.Error -> {
            Text("Error")

        }
        UiState.Loading -> {
            ShowLoading()

        }
        is UiState.Success<List<ApiSource>> -> {
            val sources = uiState.data
            LazyColumn {
                items(sources) { source ->
                    Text(
                        text = source.name,
                        modifier = Modifier
                            .clickable {
                                source.id?.let { onSourceClick(it) }
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}