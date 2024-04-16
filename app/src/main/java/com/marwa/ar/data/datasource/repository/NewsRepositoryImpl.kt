package com.marwa.ar.data.datasource.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marwa.ar.data.datasource.remote.INewsRemoteDS
import com.marwa.ar.data.datasource.repository.paging.NewsDataSource
import com.marwa.ar.data.model.Articles
import com.marwa.ar.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val iNewsRemoteDS: INewsRemoteDS,
) : INewsRepository {

    override suspend fun getNews(query: String): Flow<PagingData<Articles>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
            ),
            pagingSourceFactory = {
                NewsDataSource(query, iNewsRemoteDS)
            }

        ).flow
    }

}



