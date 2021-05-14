package com.example.rocketreserver.di.data

import com.example.rocketreserver.LaunchDetailsQuery
import com.example.rocketreserver.LaunchListQuery
import com.example.rocketreserver.LoginMutation
import com.example.rocketreserver.data.mapper.LaunchDetailsMapper
import com.example.rocketreserver.data.mapper.LaunchListMapper
import com.example.rocketreserver.data.mapper.LoginDetailsMapper
import com.example.rocketreserver.data.repository.SpaceXRepositoryImpl
import com.example.rocketreserver.data.token.TokenStorage
import com.example.rocketreserver.data.token.TokenStorageImpl
import com.example.rocketreserver.domain.mapper.Mapper
import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.domain.repository.SpaceXRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun tokenStorage(
        storage: TokenStorageImpl
    ): TokenStorage

    @Binds
    @Singleton
    abstract fun spaceXRepository(
        repository: SpaceXRepositoryImpl
    ): SpaceXRepository

    @Binds
    @Singleton
    abstract fun launchListMapper(
        mapper: LaunchListMapper
    ): Mapper<LaunchListQuery.Launches, LaunchList>

    @Binds
    @Singleton
    abstract fun launchDetailsMapper(
        mapper: LaunchDetailsMapper
    ): Mapper<LaunchDetailsQuery.Launch, LaunchDetails>

    @Binds
    @Singleton
    abstract fun loginDetailsMapper(
        mapper: LoginDetailsMapper
    ): Mapper<LoginMutation.Data, LoginDetails>
}