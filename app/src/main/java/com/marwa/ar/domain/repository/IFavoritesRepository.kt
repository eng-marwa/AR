package com.marwa.ar.domain.repository

import androidx.paging.PagingData
import com.marwa.ar.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    suspend fun addToFavorite(entity: NewsEntity):Long
    suspend fun getFavoriteItems(): Flow<PagingData<NewsEntity>>
    suspend fun removeFromFavorite(entity: NewsEntity): Int
    suspend fun getNewsById(title: String):NewsEntity
}