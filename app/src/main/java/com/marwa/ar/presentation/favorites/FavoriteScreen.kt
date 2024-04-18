package com.marwa.ar.presentation.favorites

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.marwa.ar.utils.ErrorMessage
import com.marwa.ar.utils.LoadingNextPageItem
import com.marwa.ar.utils.PageLoading

@Composable
fun FavoriteScreen(
    favoritesViewModel: FavoritesViewModel,
) {
    LaunchedEffect(Unit) {
        favoritesViewModel.viewFavoriteItems()
    }
    val newsPagingItems = favoritesViewModel.favNewsState.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(4.dp)
    ) {
        item { Spacer(modifier = Modifier.padding(4.dp)) }
        items(newsPagingItems.itemCount) { index ->
            if(favoritesViewModel.contains(newsPagingItems[index]!!.title)){
                favoritesViewModel.favoriteList.add(newsPagingItems[index]!!)
            }
            FavoriteItem(
                item = newsPagingItems[index]!!, favoritesViewModel = favoritesViewModel,
            )
            Log.d("TAG", "FavoriteScreen: ${favoritesViewModel.favoriteList}")
        }
        newsPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        PageLoading(modifier = Modifier.fillParentMaxSize())
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = newsPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = newsPagingItems.loadState.append as LoadState.Error
                    item {
                        Text(text = error.error.localizedMessage!!)
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.padding(4.dp)) }
    }
}