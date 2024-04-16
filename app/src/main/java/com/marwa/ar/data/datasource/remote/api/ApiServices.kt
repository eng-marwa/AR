package com.marwa.ar.data.datasource.remote.api

import com.marwa.ar.data.model.Articles
import com.marwa.ar.data.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    companion object {
        fun createAuthService(retrofit: Retrofit): ApiServices {
            return retrofit.create(ApiServices::class.java)
        }
    }

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
        @Query("page") pageNumber: Int
    ): NewsResponse<List<Articles>>

}