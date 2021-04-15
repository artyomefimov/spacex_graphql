package com.example.rocketreserver.di

fun allModules() = listOf(
    networkModule(),
    repositoryModule(),
    commonModule(),
    launchListModule(),
    launchDetailsModule(),
    loginModule()
)