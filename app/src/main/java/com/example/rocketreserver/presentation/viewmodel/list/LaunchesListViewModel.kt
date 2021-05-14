package com.example.rocketreserver.presentation.viewmodel.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketreserver.domain.ext.collect
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractor
import com.example.rocketreserver.domain.model.LaunchListElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesListViewModel @Inject constructor(
    private val interactor: LaunchesListInteractor
) : ViewModel() {

    private val launchesState = MutableStateFlow(listOf<LaunchListElement>())
    private val loadingState = MutableStateFlow(false)
    private val errorState = MutableStateFlow(false)
    private var cursor: String? = null
    private var hasMore: Boolean = true

    fun launchesState(): StateFlow<List<LaunchListElement>> = launchesState
    fun loadingState(): StateFlow<Boolean> = loadingState
    fun errorState(): StateFlow<Boolean> = errorState

    fun loadLaunches() {
        if (hasMore) {
            viewModelScope.launch {
                errorState.value = false
                loadingState.value = true
                interactor.getLaunches(cursor).collect(
                    onSuccess = { result ->
                        launchesState.value = launchesState.value + result.data.launches
                        cursor = result.data.cursor
                        hasMore = result.data.hasMore
                        loadingState.value = false
                    },
                    onError = {
                        errorState.value = true
                        loadingState.value = false
                    }
                )
            }
        }
    }
}
