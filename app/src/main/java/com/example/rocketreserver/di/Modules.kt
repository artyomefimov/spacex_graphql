package com.example.rocketreserver.di

fun allModules() = listOf(
    networkModule(),
    repositoryModule(),
    launchListModule(),
    launchDetailsModule(),
    loginModule()
)