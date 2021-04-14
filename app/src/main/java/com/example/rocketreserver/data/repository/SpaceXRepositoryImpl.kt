package com.example.rocketreserver.data.repository

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.example.rocketreserver.*
import com.example.rocketreserver.data.ext.checkErrors
import com.example.rocketreserver.domain.mapper.Mapper
import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.domain.model.result.ResultWrapper
import com.example.rocketreserver.domain.repository.SpaceXRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SpaceXRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val apolloClient: ApolloClient,
    private val launchListMapper: Mapper<LaunchListQuery.Launches, LaunchList>,
    private val listDetailsMapper: Mapper<LaunchDetailsQuery.Launch, LaunchDetails>,
    private val loginDetailsMapper: Mapper<LoginMutation.Data, LoginDetails>
) : SpaceXRepository {

    override suspend fun getLaunches(cursor: String?): ResultWrapper<LaunchList> {
        return performCall {
            apolloClient.query(LaunchListQuery(cursor = Input.fromNullable(cursor)))
                .await()
                .checkErrors { data -> launchListMapper.map(data.launches) }
        }
    }

    override suspend fun getLaunchDetails(launchId: String): ResultWrapper<LaunchDetails> {
        return performCall {
            apolloClient.query(LaunchDetailsQuery(id = launchId))
                .await()
                .checkErrors { data -> listDetailsMapper.map(requireNotNull(data.launch)) }
        }
    }

    override suspend fun performLogin(login: String): ResultWrapper<LoginDetails> {
        return performCall {
            apolloClient.mutate(LoginMutation(email = Input.fromNullable(login)))
                .await()
                .checkErrors { data -> loginDetailsMapper.map(data) }
        }
    }

    override suspend fun bookTrip(launchId: String): ResultWrapper<Unit> {
        return performCall {
            apolloClient.mutate(BookTripMutation(id = launchId))
                .await()
                .checkErrors { }
        }
    }

    override suspend fun cancelTrip(launchId: String): ResultWrapper<Unit> {
        return performCall {
            apolloClient.mutate(CancelTripMutation(id = launchId))
                .await()
                .checkErrors { }
        }
    }

    private suspend fun <T> performCall(call: suspend () -> ResultWrapper<T>): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                call()
            } catch (e: Throwable) {
                Log.d("LaunchList", "Failure", e)
                ResultWrapper.Error(e)
            }
        }
    }
}