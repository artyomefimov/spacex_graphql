package com.example.rocketreserver.di

import com.example.rocketreserver.domain.interactor.login.LoginInteractor
import com.example.rocketreserver.domain.interactor.login.LoginInteractorImpl
import com.example.rocketreserver.presentation.viewmodel.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun loginModule() = module {
    single<LoginInteractor> { LoginInteractorImpl(repository = get(), tokenStorage = get()) }
    viewModel { LoginViewModel(interactor = get(), context = get()) }
}