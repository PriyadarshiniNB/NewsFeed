package me.priya.newsapp.ui.screens.NewsListScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import me.priya.newsapp.data.domain.usecase.GetNewsByCountryUseCase
import me.priya.newsapp.data.domain.usecase.GetNewsByLanguageUseCase
import me.priya.newsapp.data.domain.usecase.GetNewsBySourceUseCase
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.UiState
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class NewsListViewModel  @Inject constructor(
    private val getNewsByCountryUseCase: GetNewsByCountryUseCase,
    private val getNewsByLanguageUseCase: GetNewsByLanguageUseCase,
    savedStateHandle: SavedStateHandle

): ViewModel(){

    private val country: String? = savedStateHandle["country"]
    private val language: String? = savedStateHandle["language"]
    private val isLoading = MutableStateFlow(false)
    private val articles = MutableStateFlow<List<Article>>(emptyList())
    private val error = MutableStateFlow<String?>(null)

    private var job: Job? = null

    val uiState: StateFlow<UiState<List<Article>>> =
        combine(isLoading, articles, error) { loading, data, errorMsg ->

            when {
                loading -> UiState.Loading
                errorMsg != null -> UiState.Error(errorMsg)
                else ->  UiState.Success(data)
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState.Loading
        )

    init {
        when {
            country != null -> fetchByCountry(country)
            language != null -> fetchByLanguage(language)
        }
    }


    fun fetchByCountry(country: String) {
        fetch { getNewsByCountryUseCase(country) }
    }

    fun fetchByLanguage(language: String) {
        fetch { getNewsByLanguageUseCase(language) }
    }

    private fun fetch(block: () -> Flow<List<Article>>) {

        job?.cancel()

        job = viewModelScope.launch {
            isLoading.value = true
            error.value = null

            block()
                .catch {
                    error.value = it.message ?: "Error"
                    isLoading.value = false
                }
                .collect {
                    articles.value = it
                    isLoading.value = false
                }
        }
    }
}