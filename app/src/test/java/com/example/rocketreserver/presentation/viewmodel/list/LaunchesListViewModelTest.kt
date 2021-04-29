package com.example.rocketreserver.presentation.viewmodel.list

import app.cash.turbine.test
import com.example.rocketreserver.MainCoroutineRule
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractor
import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.LaunchListElement
import com.example.rocketreserver.domain.model.result.ResultWrapper
import com.example.rocketreserver.errorResult
import io.mockk.coEvery
import io.mockk.coVerify
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
class LaunchesListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private val interactor = mockk<LaunchesListInteractor>()
    private val viewModel = LaunchesListViewModel(interactor)

    @Test
    fun `loadLaunches success causes change of launchesState`() = runBlockingTest {
        val launches = listOf(
            LaunchListElement(
                id = "id1",
                site = "site1",
                missionName = "Name1",
                missionPatch = "patch1"
            ),
            LaunchListElement(
                id = "id2",
                site = "site2",
                missionName = "Name2",
                missionPatch = "patch2"
            ),
        )
        val list = LaunchList(
            launches = launches,
            cursor = "cursor",
            hasMore = false
        )
        coEvery { interactor.getLaunches(any()) } returns ResultWrapper.Success(list)

        viewModel.loadLaunches()

        viewModel.launchesState().test {
            assertEquals(launches, expectItem())
        }
        coVerify(exactly = 1) { interactor.getLaunches(any()) }
    }

    @Test
    fun `loadLaunches error causes change of errorState`() = runBlockingTest {
        coEvery { interactor.getLaunches(any()) } returns errorResult

        viewModel.loadLaunches()

        viewModel.errorState().test {
            assertTrue(expectItem())
        }
        coVerify(exactly = 1) { interactor.getLaunches(any()) }
    }
}
