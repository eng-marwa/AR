package com.marwa.ar.data.datasource.remote

import android.util.Log
import com.marwa.ar.data.datasource.remote.api.ApiServices
import com.marwa.ar.data.model.Articles
import com.marwa.ar.data.model.NewsResponse

class NewsRemoteDSImpl(
    private val apiServices: ApiServices
) : INewsRemoteDS {


    override suspend fun getNews(query: String,apiKey:String, pageNumber: Int): NewsResponse<List<Articles>> {
        Log.d("TAG", "getNews: ")
        return apiServices.getNews(query = query, apiKey = apiKey, pageNumber = pageNumber)

    }

}