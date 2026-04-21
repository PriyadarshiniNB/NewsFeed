package me.priya.newsapp.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class GetNewsByCountryUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(country: String): Flow<List<Article>> {
        return repository.getNewsByCountry(country)
    }
}