package com.marwa.ar.domain.usecase

import android.util.Log
import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddToFavoriteUseCase(
    private val favoriteRepository: IFavoritesRepository
) {
    suspend operator fun invoke(entity: NewsEntity): Flow<Boolean> = flow {
        try {
            val insertedId = favoriteRepository.addToFavorite(entity)
            Log.d("TAG", "invoke: $insertedId")
            emit(insertedId != -1L)
        } catch (e: Exception) {
            emit(false)
        }
    }
}