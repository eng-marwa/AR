package com.marwa.ar.domain.usecase

import androidx.paging.PagingData
import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow

class ViewFavoriteItemsUseCase(private val favoriteRepository: IFavoritesRepository) {
    suspend operator fun invoke(): Flow<PagingData<NewsEntity>> {
        return favoriteRepository.getFavoriteItems()

    }
}