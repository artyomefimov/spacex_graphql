package com.example.rocketreserver.domain.interactor.details

import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper
import com.example.rocketreserver.domain.repository.SpaceXRepository

class LaunchDetailsInteractorImpl(
    private val repository: SpaceXRepository
): LaunchDetailsInteractor {

    override suspend fun getLaunchDetails(launchId: String): ResultWrapper<LaunchDetails> {
        return repository.getLaunchDetails(launchId)
    }

    override suspend fun bookTrip(launchId: String): ResultWrapper<Unit> {
        return repository.bookTrip(launchId)
    }

    override suspend fun cancelTrip(launchId: String): ResultWrapper<Unit> {
        return repository.cancelTrip(launchId)
    }

    override fun getToken(): String {
        return repository.getToken()
    }
}