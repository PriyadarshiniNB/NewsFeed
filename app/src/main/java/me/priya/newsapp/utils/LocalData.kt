package me.priya.newsapp.utils

import me.priya.newsapp.data.model.Country
import me.priya.newsapp.data.model.Language

object LocalData {

    val countries = listOf(
        Country("ar", "Argentina"),
        Country("at", "Austria"),
        Country("au", "Australia"),
        Country("be", "Belgium"),
        Country("br", "Brazil"),
        Country("ng", "Nigeria"),
        Country("us", "United States"),
        Country("in", "India")
    )

    val languages = listOf(
        Language("ar", "Arabic"),
        Language("de", "German"),
        Language("en", "English"),
        Language("es", "Spanish"),
        Language("fr", "French"),
        Language("hi", "Hindi")
    )
}