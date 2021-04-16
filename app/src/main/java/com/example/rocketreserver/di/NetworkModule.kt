package com.example.rocketreserver.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.SubscriptionTransport
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import com.example.rocketreserver.data.api.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

private const val BASE_URL = "https://apollo-fullstack-tutorial.herokuapp.com/"
private const val WEB_SOCKET_URL = "wss://apollo-fullstack-tutorial.herokuapp.com/graphql"

fun networkModule() = module {
    single { AuthorizationInterceptor(tokenStorage = get()) }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthorizationInterceptor>())
            .build()
    }
    single< SubscriptionTransport.Factory> {
        WebSocketSubscriptionTransport.Factory(
            webSocketUrl = WEB_SOCKET_URL,
            get<OkHttpClient>()
        )
    }
    single {
        ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(get())
            .subscriptionTransportFactory(get())
            .build()
    }
}
