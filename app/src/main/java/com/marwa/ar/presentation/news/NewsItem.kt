package com.marwa.ar.presentation.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.marwa.ar.base.BaseViewModel
import com.marwa.ar.domain.entity.NewsEntity
import com.marwa.ar.presentation.favorites.FavoritesViewModel
import com.marwa.ar.utils.extensions.showToast
import com.marwa.ar.utils.extensions.toDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NewsItem(
    item: NewsEntity,
    favoritesViewModel: FavoritesViewModel,
    onFavoriteClick: () -> Unit,
    isFavorite: MutableState<Boolean>
    ) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Column {

            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(item.imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconToggleButton(
                    checked = favoritesViewModel.favoriteList.contains(item),
                    onCheckedChange = { isChecked ->
                        if (isChecked) { onFavoriteClick()
                           coroutineScope.launch {
                                favoritesViewModel.newsAdded.collectLatest {
                                    when (it) {
                                        is BaseViewModel.ViewState.Loaded -> {
                                            context.showToast("Added to favorite")
                                        }

                                        is BaseViewModel.ViewState.Failed -> {
                                            context.showToast(
                                                it.throwable?.getMessage()
                                                    ?: "Failed to add to favorite"
                                            )

                                        }

                                        else -> {
                                            // Handle loading
                                        }
                                    }
                                }
                            }
                        } else {
                            context.showToast("Already in favorite")
                        }


                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(50))
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }

            Text(
                text = item.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "By: ${item.author ?: ""}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Published At: ${item.publishedAt?.toDate() ?: ""}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(4.dp)
            )

            Text(
                text = item.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )

        }
    }
}

