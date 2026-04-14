package me.priya.newsapp.data.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.priya.newsapp.data.local.entity.Article
import me.priya.newsapp.data.model.ApiSource
import me.priya.newsapp.data.repository.NewsRepository
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository : NewsRepository
){
    operator fun invoke() : Flow<List<ApiSource>>{
        return repository.getSources()
    }

}