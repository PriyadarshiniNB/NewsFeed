package me.priya.newsapp.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class GetTwoLanguagesNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(
        lang1: String,
        lang2: String
    ): Flow<List<Article>> {
        return repository.getNewsByTwoLanguages(lang1, lang2)
    }
}