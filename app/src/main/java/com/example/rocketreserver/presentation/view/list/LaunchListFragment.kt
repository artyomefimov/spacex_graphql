package com.example.rocketreserver.presentation.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rocketreserver.databinding.LaunchListFragmentBinding
import com.example.rocketreserver.domain.model.LaunchListElement
import com.example.rocketreserver.presentation.viewmodel.list.LaunchesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LaunchListFragment : Fragment() {

    private lateinit var binding: LaunchListFragmentBinding
    private var adapter: LaunchListAdapter? = null
    private val viewModel: LaunchesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LaunchListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.launchesErrorView.onRetryClicked = {
            viewModel.loadLaunches()
        }
        adapter = LaunchListAdapter(
            onEndOfListReached = { viewModel.loadLaunches() },
            onItemClicked = {
                findNavController().navigate(
                    LaunchListFragmentDirections.openLaunchDetails(launchId = it.id)
                )
            }
        )
        binding.launchesRecyclerView.adapter = adapter

        lifecycleScope.launchWhenResumed {
            viewModel.launchesState().collect(::showLaunches)
        }
        lifecycleScope.launchWhenResumed {
            viewModel.errorState().collect(::showError)
        }
        lifecycleScope.launchWhenResumed {
            viewModel.loadingState().collect(::showLoading)
        }

        viewModel.loadLaunches()
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    private fun showLaunches(launches: List<LaunchListElement>) {
        adapter?.swapData(launches)
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            launchesProgressBar.isVisible = isLoading
            launchesRecyclerView.isVisible = isLoading.not()
        }
    }

    private fun showError(hasError: Boolean) {
        with(binding) {
            launchesErrorView.isVisible = hasError
            launchesProgressBar.isVisible = hasError.not()
            launchesRecyclerView.isVisible = hasError.not()
        }
    }
}
