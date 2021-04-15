package com.example.rocketreserver.presentation.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rocketreserver.databinding.LaunchListFragmentBinding
import com.example.rocketreserver.domain.model.LaunchListElement
import com.example.rocketreserver.presentation.viewmodel.list.LaunchesListViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchListFragment : Fragment() {

    private lateinit var binding: LaunchListFragmentBinding
    private var adapter: LaunchListAdapter? = null
    private val viewModel by viewModel<LaunchesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LaunchListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.errorView.onRetryClicked = {
            binding.errorView.isInvisible = true
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
            viewModel.loadingState().collect(::showLoading)
        }
        lifecycleScope.launchWhenResumed {
            viewModel.errorState().collect(::showError)
        }
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
            progressBar.isInvisible = isLoading.not()
            launchesRecyclerView.isInvisible = isLoading
        }
    }

    private fun showError(hasError: Boolean) {
        with(binding) {
            errorView.isInvisible = hasError.not()
            progressBar.isInvisible = hasError
            launchesRecyclerView.isInvisible = hasError
        }
    }
}