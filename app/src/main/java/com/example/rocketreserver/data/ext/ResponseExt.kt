package com.example.rocketreserver.data.ext

import com.apollographql.apollo.api.Response
import com.example.rocketreserver.domain.exception.ApplicationException
import com.example.rocketreserver.domain.model.result.ResultWrapper

inline fun <T, R> Response<T>.checkErrors(successMapping: (T) -> R): ResultWrapper<R> {
    return if (data == null || hasErrors()) {
        ResultWrapper.Error(
            ApplicationException(errors?.firstOrNull()?.message.orEmpty())
        )
    } else {
        ResultWrapper.Success(successMapping(requireNotNull(data)))
    }
}
