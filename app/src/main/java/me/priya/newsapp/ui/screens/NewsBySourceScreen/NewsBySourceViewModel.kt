package me.priya.newsapp.ui.screens.NewsBySourceScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
import me.priya.newsapp.data.domain.usecase.GetNewsBySourceDetailsUseCase
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class NewsBySourceViewModel @Inject constructor(
    private val getNewsBySourceDetailsUseCase: GetNewsBySourceDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val source: String = savedStateHandle["source"] ?: ""

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

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null

            getNewsBySourceDetailsUseCase(source)
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