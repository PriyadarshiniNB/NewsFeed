package me.priya.newsapp.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class GetNewsByLanguageUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(language: String): Flow<List<Article>> {
        return repository.getNewsByLanguage(language)
    }
}