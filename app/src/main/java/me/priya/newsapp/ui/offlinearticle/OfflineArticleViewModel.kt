package me.priya.newsapp.ui.offlinearticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.priya.newsapp.data.domain.usecase.GetOfflineArticlesUseCase
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.UiState
import me.priya.newsapp.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class OfflineArticleViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val getOfflineArticlesUseCase: GetOfflineArticlesUseCase
) :
    ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val articles = MutableStateFlow<List<Article>>(emptyList())
    private val error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<UiState<List<Article>>> =
        combine(isLoading, articles, error) { loading, data, errorMsg ->
            when {
                loading -> UiState.Loading

                errorMsg != null -> UiState.Error(errorMsg)

                data.isNotEmpty() -> UiState.Success(data)

                else -> UiState.Loading
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState.Loading
        )


init {
    fetchArticles()
}

    private fun fetchArticles() {
        viewModelScope.launch {

            getOfflineArticlesUseCase()
                .catch { e ->
                   error.value = "error"
                }
                .collect {
                    articles.value = it
                }
        }
    }

}