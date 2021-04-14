package com.example.rocketreserver.data.api

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import com.example.rocketreserver.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

fun apolloClient(context: Context): ApolloClient {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(context))
        .build()
    return ApolloClient.builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/")
        .okHttpClient(okHttpClient)
        .subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory("wss://apollo-fullstack-tutorial.herokuapp.com/graphql", okHttpClient))
        .build()
}