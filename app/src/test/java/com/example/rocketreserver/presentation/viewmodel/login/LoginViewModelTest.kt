package com.example.rocketreserver.presentation.viewmodel.login

import app.cash.turbine.test
import com.example.rocketreserver.MainCoroutineRule
import com.example.rocketreserver.R
import com.example.rocketreserver.domain.interactor.login.LoginInteractor
import com.example.rocketreserver.domain.model.LoginDetails
import com.example.rocketreserver.errorResult
import com.example.rocketreserver.presentation.resources.ResourcesProvider
import com.example.rocketreserver.successResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private companion object {
        const val invalidEmail = "invalidEmail"
        const val validEmail = "me@somehost.com"
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val interactor = mockk<LoginInteractor>()
    private val resourcesProvider = mockk<ResourcesProvider>()
    private val viewModel = LoginViewModel(interactor, resourcesProvider)

    @Test
    fun `performLogin with invalid emaild changes errorTextState`() = runBlockingTest {
        every { resourcesProvider.getString(eq(R.string.invalid_email)) } returns invalidEmail

        viewModel.performLogin(invalidEmail)

        viewModel.errorTextState().test {
            assertEquals(invalidEmail, expectItem())
        }
    }

    @Test
    fun `performLogin with valid email and success saves token and causes event`() =
        runBlockingTest {
            coEvery { interactor.performLogin(any()) } returns successResult(LoginDetails(validEmail))
            coEvery { interactor.setToken(any()) } returns Unit

            viewModel.performLogin(validEmail)

            viewModel.navigateEventState().test {
                expectItem()
            }
            coVerify(exactly = 1) { interactor.performLogin(any()) }
            coVerify(exactly = 1) { interactor.setToken(any()) }
        }

    @Test
    fun `performLogin with valid email and error changes errorState`() = runBlockingTest {
        coEvery { interactor.performLogin(any()) } returns errorResult

        viewModel.performLogin(validEmail)

        viewModel.errorState().test {
            assertTrue(expectItem())
        }
        coVerify(exactly = 1) { interactor.performLogin(any()) }
    }
}
