package com.example.rocketreserver

import com.example.rocketreserver.domain.model.result.ResultWrapper

fun <T> successResult(data: T) = ResultWrapper.Success(data)

val errorResult = ResultWrapper.Error(Exception())
