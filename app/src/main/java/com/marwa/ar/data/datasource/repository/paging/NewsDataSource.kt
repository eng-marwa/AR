package com.marwa.ar.data.datasource.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marwa.ar.BuildConfig
import com.marwa.ar.data.datasource.remote.INewsRemoteDS
import com.marwa.ar.data.model.Articles
import retrofit2.HttpException
import java.io.IOException

class NewsDataSource(private val searchText: String?, private val iNewsRemoteDS: INewsRemoteDS) :
    PagingSource<Int, Articles>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        return try {
            val currentPage = params.key ?: 1
            val news = iNewsRemoteDS.getNews(
                query = searchText ?: "",
                apiKey = BuildConfig.API_KEY,
                pageNumber = currentPage
            )
            LoadResult.Page(
                data = news.articles ?: emptyList(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (news.articles.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition
    }

}