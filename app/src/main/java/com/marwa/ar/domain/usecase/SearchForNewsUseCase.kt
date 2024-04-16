package com.marwa.ar.domain.usecase

import androidx.paging.PagingData
import com.marwa.ar.data.model.Articles
import com.marwa.ar.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class SearchForNewsUseCase(
    private val newsRepository: INewsRepository
) {
    suspend operator fun invoke(query: String): Flow<PagingData<Articles>> =
        newsRepository.getNews(query)
}
