package com.example.rocketreserver.presentation.viewmodel.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketreserver.R
import com.example.rocketreserver.domain.ext.collect
import com.example.rocketreserver.domain.interactor.login.LoginInteractor
import com.example.rocketreserver.presentation.model.Event
import com.example.rocketreserver.presentation.resources.ResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel(
    private val interactor: LoginInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private companion object {
        val pattern: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }

    private val errorTextState = MutableStateFlow("")
    private val buttonLoadingState = MutableStateFlow(false)
    private val errorState = MutableStateFlow(false)
    private val navigateEventState = MutableStateFlow(Event<Unit?>(null))

    fun errorTextState(): StateFlow<String> = errorTextState
    fun buttonLoadingState(): StateFlow<Boolean> = buttonLoadingState
    fun errorState(): StateFlow<Boolean> = errorState
    fun navigateEventState(): StateFlow<Event<Unit?>> = navigateEventState

    fun performLogin(email: String) {
        if (notValidEmail(email)) {
            errorTextState.value = resourcesProvider.getString(R.string.invalid_email)
            return
        }

        viewModelScope.launch {
            errorState.value = false
            buttonLoadingState.value = true
            interactor.performLogin(email).collect(
                onSuccess = { result ->
                    interactor.setToken(result.data.login)
                    navigateEventState.value = Event(Unit)
                },
                onError = {
                    errorState.value = true
                    buttonLoadingState.value = false
                }
            )
        }
    }

    private fun notValidEmail(email: String) = pattern.matcher(email).matches().not()
}
