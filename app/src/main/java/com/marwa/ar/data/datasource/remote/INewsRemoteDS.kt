package com.marwa.ar.data.datasource.remote

import com.marwa.ar.data.model.Articles
import com.marwa.ar.data.model.NewsResponse


interface INewsRemoteDS {
    suspend fun getNews(query: String,apiKey:String, pageNumber: Int): NewsResponse<List<Articles>>
}