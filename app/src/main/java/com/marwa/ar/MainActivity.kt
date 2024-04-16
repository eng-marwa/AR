package com.marwa.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.marwa.ar.base.BaseViewModel
import com.marwa.ar.presentation.favorites.FavoriteScreen
import com.marwa.ar.presentation.favorites.FavoritesViewModel
import com.marwa.ar.presentation.news.NewsScreen
import com.marwa.ar.presentation.news.NewsViewModel
import com.marwa.ar.ui.theme.ARTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ARTabLayout()
                }
            }
        }
    }


    @Composable
    fun ARTabLayout() {
        val titles = listOf("News", "My favorites News")
        var tabIndex by remember { mutableStateOf(0) }
        val favoritesViewModel: FavoritesViewModel = getViewModel<FavoritesViewModel>()
        Scaffold(topBar = {
            TabRow(selectedTabIndex = tabIndex) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
        }, content = { padding ->
            // Use the provided contentPadding parameter
            TabContent(
                index = tabIndex,
                padding = padding,
                favoritesViewModel = favoritesViewModel
            )

        })
    }

    @Composable
    fun TabContent(
        index: Int,
        padding: PaddingValues,
        favoritesViewModel: FavoritesViewModel
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (index) {

                0 -> {
                    val viewModel: NewsViewModel = getViewModel<NewsViewModel>()
                    NewsScreen(
                        viewModel = viewModel,
                        favoritesViewModel = favoritesViewModel,
                    )
                }

                1 -> {
                    FavoriteScreen(favoritesViewModel = favoritesViewModel)
                }
            }
        }
    }




    @Preview
    @Composable
    fun DefaultPreview() {
        ARTheme {
            ARTabLayout()
        }
    }
}