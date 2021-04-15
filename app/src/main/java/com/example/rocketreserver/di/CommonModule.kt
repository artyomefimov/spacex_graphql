package com.example.rocketreserver.di

import com.example.rocketreserver.data.mapper.LaunchDetailsMapper
import com.example.rocketreserver.data.mapper.LaunchListMapper
import com.example.rocketreserver.data.mapper.LoginDetailsMapper
import com.example.rocketreserver.data.repository.SpaceXRepositoryImpl
import com.example.rocketreserver.data.token.TokenStorage
import com.example.rocketreserver.data.token.TokenStorageImpl
import com.example.rocketreserver.domain.mapper.Mapper
import com.example.rocketreserver.domain.repository.SpaceXRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val LAUNCH_LIST_MAPPER = "LAUNCH_LIST_MAPPER"
private const val LAUNCH_DETAILS_MAPPER = "LAUNCH_DETAILS_MAPPER"
private const val LOGIN_DETAILS_MAPPER = "LAUNCH_DETAILS_MAPPER"

fun repositoryModule() = module {
    single(qualifier = named(LAUNCH_LIST_MAPPER)) {
        LaunchListMapper()
    } bind Mapper::class
    single(qualifier = named(LAUNCH_DETAILS_MAPPER)) {
        LaunchDetailsMapper()
    } bind Mapper::class
    single(qualifier = named(LOGIN_DETAILS_MAPPER)) {
        LoginDetailsMapper()
    } bind Mapper::class
    single<TokenStorage> { TokenStorageImpl(context = get()) }
    single<SpaceXRepository> {
        SpaceXRepositoryImpl(
            dispatcher = Dispatchers.IO,
            apolloClient = get(),
            launchListMapper = get(qualifier = named(LAUNCH_LIST_MAPPER)),
            listDetailsMapper = get(qualifier = named(LAUNCH_DETAILS_MAPPER)),
            loginDetailsMapper = get(qualifier = named(LOGIN_DETAILS_MAPPER)),
            tokenStorage = get()
        )
    }
}