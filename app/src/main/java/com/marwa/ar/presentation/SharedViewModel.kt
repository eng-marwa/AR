package com.marwa.ar.presentation

import androidx.lifecycle.ViewModel
import com.marwa.ar.data.model.Articles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _favoriteItems = MutableStateFlow<List<Articles>>(emptyList())
    val favoriteItems: StateFlow<List<Articles>> = _favoriteItems

    fun addFavoriteItem(item: Articles) {
        val currentItems = _favoriteItems.value.toMutableList()
        currentItems.add(item)
        _favoriteItems.value = currentItems
    }

    fun removeFavoriteItem(item: Articles) {
        val currentItems = _favoriteItems.value.toMutableList()
        currentItems.remove(item)
        _favoriteItems.value = currentItems
    }

    fun isFavorite(item: Articles): Boolean {
        return _favoriteItems.value.contains(item)
    }
}