package com.example.rocketreserver.domain.ext

import com.example.rocketreserver.domain.model.result.ResultWrapper

inline fun <T> ResultWrapper<T>.collect(
    onSuccess: (ResultWrapper.Success<T>) -> Unit,
    onError: (ResultWrapper.Error) -> Unit
) {
    when (this) {
        is ResultWrapper.Success -> onSuccess(this)
        is ResultWrapper.Error -> onError(this)
    }
}
