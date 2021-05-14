package com.example.rocketreserver.data.api

import com.example.rocketreserver.data.token.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", tokenStorage.getToken())
            .build()

        return chain.proceed(request)
    }
}
