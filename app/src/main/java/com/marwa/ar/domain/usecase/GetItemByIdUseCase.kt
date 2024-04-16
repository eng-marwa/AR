package com.marwa.ar.domain.usecase

import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetItemByIdUseCase (
    private val favoriteRepository: IFavoritesRepository
) {
    suspend operator fun invoke(title: String): Flow<NewsEntity?> = flow {
        try {
            val newsEntity =  favoriteRepository.getNewsById(title)
           emit(newsEntity)
        } catch (e: Exception) {
            emit(null)
        }
    }
}