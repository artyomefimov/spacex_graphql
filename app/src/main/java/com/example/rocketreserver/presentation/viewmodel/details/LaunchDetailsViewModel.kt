package com.example.rocketreserver.presentation.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketreserver.R
import com.example.rocketreserver.domain.ext.collect
import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractor
import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.Rocket
import com.example.rocketreserver.presentation.model.Event
import com.example.rocketreserver.presentation.resources.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LaunchDetailsViewModel(
    private val interactor: LaunchDetailsInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private val launchDetailsState = MutableStateFlow<LaunchDetails?>(null)
    private val loadingState = MutableStateFlow(false)
    private val buttonLoadingState = MutableStateFlow(false)
    private val errorState = MutableStateFlow(false)
    private val buttonTextState = MutableStateFlow("")
    private val rocketInfoState = MutableStateFlow("")
    private val navigateEventState = MutableStateFlow(Event<Unit?>(null))

    private var isBooked = false
        set(value) {
            field = value
            resolveButtonText(value)
        }
    var launchId = ""

    init {
        loadLaunchDetails()
    }

    fun launchDetailsState(): StateFlow<LaunchDetails?> = launchDetailsState
    fun loadingState(): StateFlow<Boolean> = loadingState
    fun buttonLoadingState(): StateFlow<Boolean> = buttonLoadingState
    fun errorState(): StateFlow<Boolean> = errorState
    fun buttonTextState(): StateFlow<String> = buttonTextState
    fun rocketInfoState(): StateFlow<String> = rocketInfoState
    fun navigateEventState(): StateFlow<Event<Unit?>> = navigateEventState

    fun performBookOperation() {
        val token = interactor.getToken()
        if (token.isEmpty()) {
            navigateEventState.value = Event(Unit)
        } else {
            if (isBooked) {
                cancelTrip()
            } else {
                bookTrip()
            }
        }
    }

    private fun loadLaunchDetails() {
        viewModelScope.launch {
            errorState.value = false
            loadingState.value = true
            interactor.getLaunchDetails(launchId).collect(
                onSuccess = { result ->
                    launchDetailsState.value = result.data
                    isBooked = result.data.isBooked
                    resolveRocketInfo(result.data.rocket)
                    loadingState.value = false
                },
                onError = {
                    errorState.value = true
                    loadingState.value = false
                }
            )
        }
    }

    private fun resolveRocketInfo(rocket: Rocket) {
        rocketInfoState.value = resourcesProvider.getString(
            R.string.rocketName,
            rocket.name,
            rocket.type
        )
    }

    private fun resolveButtonText(isBooked: Boolean) {
        buttonTextState.value = if (isBooked) {
            resourcesProvider.getString(R.string.cancel)
        } else {
            resourcesProvider.getString(R.string.book_now)
        }
    }

    private fun bookTrip() {
        viewModelScope.launch {
            buttonLoadingState.value = true
            interactor.bookTrip(launchId).collect(
                onSuccess = {
                    isBooked = true
                    buttonLoadingState.value = false
                },
                onError = {
                    errorState.value = true
                    buttonLoadingState.value = false
                }
            )
        }
    }

    private fun cancelTrip() {
        viewModelScope.launch {
            buttonLoadingState.value = true
            interactor.cancelTrip(launchId).collect(
                onSuccess = {
                    isBooked = false
                    buttonLoadingState.value = false
                },
                onError = {
                    errorState.value = true
                    buttonLoadingState.value = false
                }
            )
        }
    }
}
