package com.example.rocketreserver.di

import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractor
import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractorImpl
import com.example.rocketreserver.presentation.viewmodel.details.LaunchDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun launchDetailsModule() = module {
    single<LaunchDetailsInteractor> { LaunchDetailsInteractorImpl(repository = get()) }
    viewModel { LaunchDetailsViewModel(interactor = get(), resourcesProvider = get()) }
}
