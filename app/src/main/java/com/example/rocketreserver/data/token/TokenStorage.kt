package com.example.rocketreserver.data.token

interface TokenStorage {

    fun getToken(): String

    fun setToken(token: String)

    fun removeToken()
}
