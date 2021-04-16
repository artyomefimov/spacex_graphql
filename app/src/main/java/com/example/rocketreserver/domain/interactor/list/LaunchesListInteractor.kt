package com.example.rocketreserver.domain.interactor.list

import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.result.ResultWrapper

interface LaunchesListInteractor {

    suspend fun getLaunches(cursor: String?): ResultWrapper<LaunchList>
}
