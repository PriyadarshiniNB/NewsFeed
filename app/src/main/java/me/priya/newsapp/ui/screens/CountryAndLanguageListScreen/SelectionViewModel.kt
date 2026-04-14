package me.priya.newsapp.ui.screens.CountryAndLanguageListScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.priya.newsapp.data.model.Country
import me.priya.newsapp.data.model.Language
import me.priya.newsapp.utils.LocalData
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor() : ViewModel() {

    private val _countries = MutableStateFlow(LocalData.countries)
    val countries: StateFlow<List<Country>> = _countries

    private val _languages = MutableStateFlow(LocalData.languages)
    val languages: StateFlow<List<Language>> = _languages
}