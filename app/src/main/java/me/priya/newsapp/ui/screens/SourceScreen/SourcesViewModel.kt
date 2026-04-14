package me.priya.newsapp.ui.screens.SourceScreen

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
import me.priya.newsapp.data.domain.usecase.GetSourcesUseCase
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.model.ApiSource
import me.priya.newsapp.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase
): ViewModel(){

    private val isLoading = MutableStateFlow<Boolean>(false)
    private val articles = MutableStateFlow<List<ApiSource>>(emptyList())
    private val error = MutableStateFlow<String?>(null)

    val uiState : StateFlow<UiState<List<ApiSource>>> =
        combine(isLoading, articles, error) { loading, data, errorMsg ->
            when {
                loading -> UiState.Loading

                errorMsg != null -> UiState.Error(errorMsg)

                data.isNotEmpty() -> UiState.Success(data)

                else -> UiState.Success(emptyList())


            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState.Loading
        )


    fun fetch(country : String = "us") {
        viewModelScope.launch {
            isLoading.value = true
            getSourcesUseCase().catch {
                error.value = it.message ?: "error"
            }.collect {
                articles.value = it
                isLoading.value = false
            }
        }


    }



}