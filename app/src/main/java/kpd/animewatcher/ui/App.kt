package kpd.animewatcher.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import kpd.animewatcher.di.localRepositoryModule
import kpd.animewatcher.di.remoteRepositoryModule
import kpd.animewatcher.di.repositoryModule
import kpd.animewatcher.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                remoteRepositoryModule,
                localRepositoryModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}