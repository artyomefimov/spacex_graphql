package com.example.rocketreserver.di

import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractor
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractorImpl
import com.example.rocketreserver.presentation.viewmodel.list.LaunchesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun launchListModule() = module {
    single<LaunchesListInteractor> { LaunchesListInteractorImpl(repository = get()) }
    viewModel { LaunchesListViewModel(interactor = get()) }
}