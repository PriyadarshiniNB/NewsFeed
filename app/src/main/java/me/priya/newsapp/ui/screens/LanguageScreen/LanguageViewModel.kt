package me.priya.newsapp.ui.screens.LanguageScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.UiState
import javax.inject.Inject
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.priya.newsapp.data.domain.usecase.GetTwoLanguagesNewsUseCase

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getTwoLanguagesNewsUseCase: GetTwoLanguagesNewsUseCase
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val articles = MutableStateFlow<List<Article>>(emptyList())
    private val error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<UiState<List<Article>>> =
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

    fun fetchTwoLanguages(lang1: String, lang2: String) {
        viewModelScope.launch {

            isLoading.value = true
            error.value = null

            getTwoLanguagesNewsUseCase(lang1, lang2)
                .catch {
                    error.value = it.message ?: "Error loading languages"
                    isLoading.value = false
                }
                .collect {
                    articles.value = it
                    isLoading.value = false
                }
        }
    }
}