package com.marwa.ar.data.datasource.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marwa.ar.data.datasource.local.db.NewsDao
import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val newsDao: NewsDao
) : IFavoritesRepository {
    override suspend fun addToFavorite(entity: NewsEntity): Long {
        Log.d("TAG", "addToFavorite: ")
        return newsDao.insertNews(entity)
    }

    override suspend fun getFavoriteItems(): Flow<PagingData<NewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                enablePlaceholders = false,
            ),
            pagingSourceFactory =
            {
               newsDao.getFavoriteItems()
            }
        ).flow
    }

    override suspend fun removeFromFavorite(entity: NewsEntity) =
        newsDao.deleteNews(entity)

    override suspend fun getNewsById(title: String) = newsDao.getNewsById(title)

}


