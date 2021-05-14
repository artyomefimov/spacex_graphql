package com.example.rocketreserver.domain.interactor.list

import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.result.ResultWrapper
import com.example.rocketreserver.domain.repository.SpaceXRepository
import javax.inject.Inject

class LaunchesListInteractorImpl @Inject constructor(
    private val repository: SpaceXRepository
): LaunchesListInteractor {

    override suspend fun getLaunches(cursor: String?): ResultWrapper<LaunchList> {
        return repository.getLaunches(cursor)
    }
}
