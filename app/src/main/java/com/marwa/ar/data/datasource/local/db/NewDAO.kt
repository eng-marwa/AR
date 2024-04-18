package com.marwa.ar.data.datasource.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marwa.ar.domain.entity.NewsEntity


@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity): Long

    @Query("SELECT * FROM NEWS_TABLE ORDER BY id DESC")
    fun getFavoriteItems(): PagingSource<Int, NewsEntity>

    @Delete
    suspend fun deleteNews(entity: NewsEntity): Int

    @Query("SELECT * FROM NEWS_TABLE WHERE title = :title")
    suspend fun getNewsById(title: String): NewsEntity

    @Query("SELECT * FROM NEWS_TABLE")
    suspend fun getAllFavoriteItems(): List<NewsEntity>


}