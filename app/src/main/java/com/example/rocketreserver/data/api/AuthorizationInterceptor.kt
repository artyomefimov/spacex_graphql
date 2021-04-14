package com.example.rocketreserver.data.api

import android.content.Context
import com.example.rocketreserver.User
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", User.getToken(context).orEmpty())
            .build()

        return chain.proceed(request)
    }
}