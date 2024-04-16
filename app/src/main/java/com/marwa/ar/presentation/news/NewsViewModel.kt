package com.marwa.ar.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.log
import com.marwa.ar.data.model.Articles
import com.marwa.ar.domain.usecase.SearchForNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NewsViewModel(
    private val searchForNewsUseCase: SearchForNewsUseCase,
) : ViewModel() {
    private val _newsState: MutableStateFlow<PagingData<Articles>> =
        MutableStateFlow(value = PagingData.empty())
    val newsState: MutableStateFlow<PagingData<Articles>> get() = _newsState


    suspend fun getNews(query: String) {
        Log.d("TAG", "getNews: $query")
        viewModelScope.launch {
            searchForNewsUseCase(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _newsState.value = it
                }
        }
    }

}