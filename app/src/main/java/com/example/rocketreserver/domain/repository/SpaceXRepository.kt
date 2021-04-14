package com.example.rocketreserver.domain.repository

import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper

interface SpaceXRepository {

    suspend fun getLaunches(cursor: String?): ResultWrapper<LaunchList>

    suspend fun getLaunchDetails(launchId: String): ResultWrapper<LaunchDetails>

    suspend fun performLogin(login: String): ResultWrapper<LoginDetails>

    suspend fun bookTrip(launchId: String): ResultWrapper<Unit>

    suspend fun cancelTrip(launchId: String): ResultWrapper<Unit>
}