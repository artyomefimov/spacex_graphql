package com.example.rocketreserver.domain.interactor.login

import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper
import com.example.rocketreserver.domain.repository.SpaceXRepository

class LoginInteractorImpl(
    private val repository: SpaceXRepository
): LoginInteractor {

    override suspend fun performLogin(login: String): ResultWrapper<LoginDetails> {
        return repository.performLogin(login)
    }
}