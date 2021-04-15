package com.example.rocketreserver

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import com.example.rocketreserver.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(
                listOf(
                    networkModule(),
                    repositoryModule(),
                    interactorModule(),
                    launchListModule(),
                    launchDetailsModule()
                )
            )
        }
    }

    override fun newImageLoader() = ImageLoader.Builder(this)
        .crossfade(false)
        .logger(DebugLogger())
        .build()
}