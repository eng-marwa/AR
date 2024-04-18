package com.marwa.ar.di

import com.marwa.ar.data.datasource.local.db.NewsDao
import com.marwa.ar.data.datasource.local.db.NewsRoomDatabase
import com.marwa.ar.data.datasource.remote.INewsRemoteDS
import com.marwa.ar.data.datasource.remote.NewsRemoteDSImpl
import com.marwa.ar.data.datasource.remote.api.ApiServices
import com.marwa.ar.data.datasource.remote.api.AuthInterceptor
import com.marwa.ar.data.datasource.remote.api.RetrofitClient
import com.marwa.ar.data.datasource.repository.FavoritesRepositoryImpl
import com.marwa.ar.data.datasource.repository.NewsRepositoryImpl
import com.marwa.ar.domain.repository.IFavoritesRepository
import com.marwa.ar.domain.repository.INewsRepository
import com.marwa.ar.domain.usecase.AddToFavoriteUseCase
import com.marwa.ar.domain.usecase.GetItemByIdUseCase
import com.marwa.ar.domain.usecase.RemoveFromFavoriteUseCase
import com.marwa.ar.domain.usecase.SearchForNewsUseCase
import com.marwa.ar.domain.usecase.ViewAllFavoritesUseCase
import com.marwa.ar.domain.usecase.ViewFavoriteItemsUseCase
import com.marwa.ar.presentation.favorites.FavoritesViewModel
import com.marwa.ar.presentation.news.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { FavoritesViewModel(get(), get(), get(), get(), get()) }

}
val repositoryModule = module {
    single<INewsRepository> {
        NewsRepositoryImpl(get())
    }

    single<IFavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }
}
val useCasesModule = module {
    factory { SearchForNewsUseCase(get()) }
    factory { AddToFavoriteUseCase(get()) }
    factory { ViewFavoriteItemsUseCase(get()) }
    factory { RemoveFromFavoriteUseCase(get()) }
    factory { GetItemByIdUseCase(get()) }
    factory { ViewAllFavoritesUseCase(get()) }
}
val dataSourceModule = module {
    factory<INewsRemoteDS> { NewsRemoteDSImpl(get()) }
}


val networkModule = module {
    single { AuthInterceptor() }

    single { RetrofitClient.provideOkHttpClient(authInterceptor = get()) }
    single { RetrofitClient.provideRetrofit(okHttpClient = get()) }
    single { ApiServices.createAuthService(retrofit = get()) }
}

val roomDatabaseModule = module {
    fun provideCartDao(dataBase: NewsRoomDatabase): NewsDao {
        return dataBase.newsDao()
    }

    single { NewsRoomDatabase.getInstance(androidApplication()) }
    single { provideCartDao(get()) }

}