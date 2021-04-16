package com.example.rocketreserver.domain.interactor.login

import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper

interface LoginInteractor {

    suspend fun performLogin(login: String): ResultWrapper<LoginDetails>

    fun setToken(login: String)
}
