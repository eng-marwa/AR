package com.marwa.ar.domain.usecase

import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.repository.IFavoritesRepository

class ViewAllFavoritesUseCase(private val favoriteRepository: IFavoritesRepository) {
    suspend operator fun invoke(): List<NewsEntity> {
        return favoriteRepository.getAllFavoriteItems()
    }
}