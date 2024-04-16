package com.marwa.ar

import android.app.Application
import com.marwa.ar.di.dataSourceModule
import com.marwa.ar.di.networkModule
import com.marwa.ar.di.repositoryModule
import com.marwa.ar.di.roomDatabaseModule
import com.marwa.ar.di.useCasesModule
import com.marwa.ar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ARApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this)
    }

    private fun startKoin(arApplication: ARApplication) {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(arApplication)
            modules(viewModelModule, networkModule, useCasesModule, repositoryModule,dataSourceModule,roomDatabaseModule)
        }
    }
}