package com.example.rocketreserver.di

import com.example.rocketreserver.presentation.resources.ResourcesProvider
import com.example.rocketreserver.presentation.resources.ResourcesProviderImpl
import org.koin.dsl.module

fun commonModule() = module {
    single<ResourcesProvider> { ResourcesProviderImpl(context = get()) }
}
