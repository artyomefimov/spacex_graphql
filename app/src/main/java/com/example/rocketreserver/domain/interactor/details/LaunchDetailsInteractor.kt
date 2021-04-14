package com.example.rocketreserver.domain.interactor.details

import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper

interface LaunchDetailsInteractor {

    suspend fun getLaunchDetails(launchId: String): ResultWrapper<LaunchDetails>

    suspend fun bookTrip(launchId: String): ResultWrapper<Unit>

    suspend fun cancelTrip(launchId: String): ResultWrapper<Unit>
}