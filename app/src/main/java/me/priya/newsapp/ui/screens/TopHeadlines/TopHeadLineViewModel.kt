package me.priya.newsapp.ui.screens.TopHeadlines

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
import me.priya.newsapp.data.domain.usecase.GetTopHeadlinesUseCase
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.model.ApiArticle
import me.priya.newsapp.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class TopHeadLineViewModel @Inject constructor(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) : ViewModel(){

   private val isLoading = MutableStateFlow<Boolean>(false)
    private val articles = MutableStateFlow<List<Article>>(emptyList())
    private val error = MutableStateFlow<String?>(null)

    init {
        fetch()
    }

    val uiState : StateFlow<UiState<List<Article>>> =
        combine(isLoading, articles, error) { loading, data, errorMsg ->
            when {
                loading -> UiState.Loading

                errorMsg != null -> UiState.Error(errorMsg)

                else -> UiState.Success(data)

            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState.Loading
        )


    fun fetch(country: String = "us") {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            getTopHeadlinesUseCase(country)
                .catch {
                    error.value = it.message ?: "error"
                    isLoading.value = false
                }
                .collect {
                    articles.value = it
                    isLoading.value = false
                }
        }
    }

}