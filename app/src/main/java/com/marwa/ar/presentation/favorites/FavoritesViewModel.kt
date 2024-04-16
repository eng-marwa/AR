package com.marwa.ar.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marwa.ar.base.BaseException
import com.marwa.ar.base.BaseViewModel
import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.domain.usecase.AddToFavoriteUseCase
import com.marwa.ar.domain.usecase.GetItemByIdUseCase
import com.marwa.ar.domain.usecase.RemoveFromFavoriteUseCase
import com.marwa.ar.domain.usecase.ViewFavoriteItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val viewFavoriteItemsUseCase: ViewFavoriteItemsUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModel() {

    val favoriteList = mutableListOf<NewsEntity>()

    private val _newsAddedState =
        MutableStateFlow<BaseViewModel.ViewState<Boolean>>(BaseViewModel.ViewState.Empty())
    val newsAdded: MutableStateFlow<BaseViewModel.ViewState<Boolean>> get() = _newsAddedState

    fun addToFavorite(
        entity: NewsEntity,
    ) {
        viewModelScope.launch {
            addToFavoriteUseCase(entity)
                .flowOn(Dispatchers.IO)
                .collect { success ->
                    if (success) {
                        if (!favoriteList.contains(entity)) {
                            favoriteList.add(entity)
                        }
                        _newsAddedState.value = BaseViewModel.ViewState.Loaded(data = true)
                    } else {
                        _newsAddedState.value =
                            BaseViewModel.ViewState.Failed(throwable = BaseException(message = "Failed to add to favorite"))
                    }
                }
        }
    }

    private val _favNewsState: MutableStateFlow<PagingData<NewsEntity>> =
        MutableStateFlow(value = PagingData.empty())
    val favNewsState: MutableStateFlow<PagingData<NewsEntity>> get() = _favNewsState

    fun viewFavoriteItems() {
        viewModelScope.launch {
            viewFavoriteItemsUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _favNewsState.value = it
                }
        }
    }

    private val _newsRemovedState =
        MutableStateFlow<BaseViewModel.ViewState<Boolean>>(BaseViewModel.ViewState.Empty())
    val newsRemovedState: MutableStateFlow<BaseViewModel.ViewState<Boolean>> get() = _newsRemovedState
    fun removeFromFavorite(entity: NewsEntity) {
        viewModelScope.launch {
            removeFromFavoriteUseCase(entity)
                .flowOn(Dispatchers.IO)
                .collect { success ->
                    if (success) {
                        favoriteList.remove(entity)
                        Log.d("TAG", "removeFromFavorite:${favoriteList} ")
                        _newsRemovedState.value = BaseViewModel.ViewState.Loaded(data = true)
                    } else {
                        _newsRemovedState.value =
                            BaseViewModel.ViewState.Failed(throwable = BaseException(message = "Failed to remove from favorite"))
                    }
                }
        }
    }

    fun isFavorite(entity: NewsEntity) {
        viewModelScope.launch {
            getItemByIdUseCase(entity.title)
                .flowOn(Dispatchers.IO)
                .collect { entity->
                    if (entity!= null && !favoriteList.contains(entity)) {
                        favoriteList.add(entity)
                    }
                    Log.d("TAG", "isFavorite: ${favoriteList}")
                }
        }
    }

}