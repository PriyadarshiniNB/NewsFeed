package me.priya.newsapp.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class GetNewsBySourceUseCase  @Inject constructor(
    private val repository : NewsRepository
){
    operator fun invoke(source : String) : Flow<List<Article>> {
        return repository.getTopHeadlines(source)
    }

}