package com.example.rocketreserver.presentation.viewmodel.details

import app.cash.turbine.test
import com.example.rocketreserver.MainCoroutineRule
import com.example.rocketreserver.domain.interactor.details.LaunchDetailsInteractor
import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.domain.model.Rocket
import com.example.rocketreserver.errorResult
import com.example.rocketreserver.presentation.resources.ResourcesProvider
import com.example.rocketreserver.successResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class LaunchDetailsViewModelTest {

    private companion object {
        const val correctToken = "correct token"
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val interactor = mockk<LaunchDetailsInteractor>()
    private val resourcesProvider = mockk<ResourcesProvider>()
    private val viewModel = LaunchDetailsViewModel(interactor, resourcesProvider)

    @Before
    fun setup() {
        every { resourcesProvider.getString(any()) } returns ""
    }

    @Test
    fun `loadLaunchDetails success causes change of launchDetailsState`() = runBlockingTest {
        val details = LaunchDetails(
            id = "id",
            site = "site",
            missionName = "missionName",
            missionPatch = "missionPatch",
            isBooked = false,
            rocket = Rocket(name = "name", type = "type")
        )
        coEvery { interactor.getLaunchDetails(any()) } returns successResult(details)

        viewModel.loadLaunchDetails()

        viewModel.launchDetailsState().test {
            assertEquals(details, expectItem())
        }
        coVerify(exactly = 1) { interactor.getLaunchDetails(any()) }
    }

    @Test
    fun `loadLaunchDetails error causes change of errorState`() = runBlockingTest {
        coEvery { interactor.getLaunchDetails(any()) } returns errorResult

        viewModel.loadLaunchDetails()

        viewModel.errorState().test {
            assertTrue(expectItem())
        }
        coVerify(exactly = 1) { interactor.getLaunchDetails(any()) }
    }

    @Test
    fun `performBookOperation empty token causes change of navigateEventState`() = runBlockingTest {
        coEvery { interactor.getToken() } returns ""

        viewModel.performBookOperation()

        viewModel.navigateEventState().test {
            expectItem()
        }
        coVerify(exactly = 1) { interactor.getToken() }
    }

    @Test
    fun `performBookOperation isBooked performs cancelTrip`() {
        coEvery { interactor.getToken() } returns correctToken
        coEvery { interactor.cancelTrip(any()) } returns errorResult
        viewModel.isBooked = true

        viewModel.performBookOperation()

        coVerify(exactly = 1) { interactor.cancelTrip(any()) }
    }

    @Test
    fun `performBookOperation isNotBooked performs bookTrip`() {
        coEvery { interactor.getToken() } returns correctToken
        coEvery { interactor.bookTrip(any()) } returns errorResult
        viewModel.isBooked = false

        viewModel.performBookOperation()

        coVerify(exactly = 1) { interactor.bookTrip(any()) }
    }

    @Test
    fun `performBookOperation isBooked+success performs cancelTrip and changes isBooked`() {
        coEvery { interactor.getToken() } returns correctToken
        coEvery { interactor.cancelTrip(any()) } returns successResult(Unit)
        viewModel.isBooked = true

        viewModel.performBookOperation()

        coVerify(exactly = 1) { interactor.cancelTrip(any()) }
        assertFalse(viewModel.isBooked)
    }

    @Test
    fun `performBookOperation isBooked+error performs cancelTrip and changes errorState`() =
        runBlockingTest {
            coEvery { interactor.getToken() } returns correctToken
            coEvery { interactor.cancelTrip(any()) } returns errorResult
            viewModel.isBooked = true

            viewModel.performBookOperation()

            viewModel.errorState().test {
                assertTrue(expectItem())
            }
            coVerify(exactly = 1) { interactor.cancelTrip(any()) }
            assertTrue(viewModel.isBooked)
        }

    @Test
    fun `performBookOperation isNotBooked+success performs bookTrip and changes isBooked`() {
        coEvery { interactor.getToken() } returns correctToken
        coEvery { interactor.bookTrip(any()) } returns successResult(Unit)
        viewModel.isBooked = false

        viewModel.performBookOperation()

        coVerify(exactly = 1) { interactor.bookTrip(any()) }
        assertTrue(viewModel.isBooked)
    }

    @Test
    fun `performBookOperation isNotBooked+error performs bookTrip and changes errorState`() =
        runBlockingTest {
            coEvery { interactor.getToken() } returns correctToken
            coEvery { interactor.bookTrip(any()) } returns errorResult
            viewModel.isBooked = false

            viewModel.performBookOperation()

            viewModel.errorState().test {
                assertTrue(expectItem())
            }
            coVerify(exactly = 1) { interactor.bookTrip(any()) }
            assertFalse(viewModel.isBooked)
        }
}
