package com.marwa.ar.domain.usecase

import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveFromFavoriteUseCase(
    private val favoriteRepository: IFavoritesRepository
) {
    suspend operator fun invoke(entity: NewsEntity): Flow<Boolean> = flow {
        try {
            val removedId = favoriteRepository.removeFromFavorite(entity)
            emit(removedId != -1)
        } catch (e: Exception) {
            emit(false)
        }
    }
}