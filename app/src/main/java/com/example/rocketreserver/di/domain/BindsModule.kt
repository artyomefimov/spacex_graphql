package com.example.rocketreserver.di.domain

import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractor
import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractorImpl
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractor
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractorImpl
import com.example.rocketreserver.domain.interactor.login.LoginInteractor
import com.example.rocketreserver.domain.interactor.login.LoginInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun listInteractor(
        interactor: LaunchesListInteractorImpl
    ): LaunchesListInteractor

    @Binds
    @Singleton
    abstract fun detailsInteractor(
        interactor: LaunchDetailsInteractorImpl
    ): LaunchDetailsInteractor

    @Binds
    @Singleton
    abstract fun loginInteractor(
        interactor: LoginInteractorImpl
    ): LoginInteractor
}