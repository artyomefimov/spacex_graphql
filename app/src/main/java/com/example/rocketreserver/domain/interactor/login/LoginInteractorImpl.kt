package com.example.rocketreserver.domain.interactor.login

import com.example.rocketreserver.data.token.TokenStorage
import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper
import com.example.rocketreserver.domain.repository.SpaceXRepository
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val repository: SpaceXRepository,
    private val tokenStorage: TokenStorage
): LoginInteractor {

    override suspend fun performLogin(login: String): ResultWrapper<LoginDetails> {
        return repository.performLogin(login)
    }

    override fun setToken(login: String) {
        tokenStorage.setToken(login)
    }
}
