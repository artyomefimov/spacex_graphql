package com.example.rocketreserver.di.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.SubscriptionTransport
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import com.example.rocketreserver.data.api.AuthorizationInterceptor
import com.example.rocketreserver.data.token.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {

    private companion object {
        const val BASE_URL = "https://apollo-fullstack-tutorial.herokuapp.com/"
        const val WEB_SOCKET_URL = "wss://apollo-fullstack-tutorial.herokuapp.com/graphql"
    }

    @Provides
    @Singleton
    fun authInterceptor(
        tokenStorage: TokenStorage
    ): AuthorizationInterceptor {
        return AuthorizationInterceptor(tokenStorage)
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun okHttpClient(
        authInterceptor: AuthorizationInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun subscriptionTransportFactory(
        okHttpClient: OkHttpClient
    ): SubscriptionTransport.Factory {
        return WebSocketSubscriptionTransport.Factory(
            webSocketUrl = WEB_SOCKET_URL,
            webSocketConnectionFactory = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun apolloClient(
        okHttpClient: OkHttpClient,
        subscriptionTransportFactory: SubscriptionTransport.Factory
    ): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .subscriptionTransportFactory(subscriptionTransportFactory)
            .build()
    }

    @Provides
    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}