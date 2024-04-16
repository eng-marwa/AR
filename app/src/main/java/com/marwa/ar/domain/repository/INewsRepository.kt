package com.marwa.ar.domain.repository

import androidx.paging.PagingData
import com.marwa.ar.data.model.Articles
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    suspend fun getNews(query: String): Flow<PagingData<Articles>>
}