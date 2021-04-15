package com.example.rocketreserver.domain.interactor

import com.example.rocketreserver.domain.model.result.ResultWrapper

interface Interactor<out T> {

    operator fun invoke(): ResultWrapper<T>
}