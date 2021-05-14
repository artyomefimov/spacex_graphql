package com.example.rocketreserver.di

import com.example.rocketreserver.presentation.resources.ResourcesProvider
import com.example.rocketreserver.presentation.resources.ResourcesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    @Singleton
    abstract fun resourcesProvider(
        provider: ResourcesProviderImpl
    ): ResourcesProvider
}
