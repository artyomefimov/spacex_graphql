package com.example.rocketreserver.domain.model.result

sealed class ResultWrapper<out T> {

    open val isSuccess: Boolean = true

    data class Success<out T>(val data: T): ResultWrapper<T>() {
        override val isSuccess: Boolean
            get() = true
    }

    data class Error(val error: Throwable): ResultWrapper<Nothing>() {
        override val isSuccess: Boolean
            get() = false
    }
}
