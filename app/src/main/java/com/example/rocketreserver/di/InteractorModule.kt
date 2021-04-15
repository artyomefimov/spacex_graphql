package com.example.rocketreserver.di

import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractor
import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractorImpl
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractor
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractorImpl
import com.example.rocketreserver.domain.interactor.login.LoginInteractor
import com.example.rocketreserver.domain.interactor.login.LoginInteractorImpl
import com.example.rocketreserver.domain.repository.SpaceXRepository
import org.koin.dsl.module

fun interactorModule() = module {
    single<LaunchDetailsInteractor> {
        LaunchDetailsInteractorImpl(repository = get())
    }
    single<LoginInteractor> {
        LoginInteractorImpl(repository = get())
    }
}