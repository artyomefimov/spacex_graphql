package com.example.rocketreserver

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger

class App : Application(), ImageLoaderFactory {

    override fun newImageLoader() = ImageLoader.Builder(this)
        .crossfade(false)
        .logger(DebugLogger())
        .build()
}