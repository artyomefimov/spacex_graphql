package com.example.rocketreserver.presentation.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LaunchDetailsFragmentBinding
import com.example.rocketreserver.domain.model.LaunchDetails
import com.example.rocketreserver.presentation.ext.collectEvent
import com.example.rocketreserver.presentation.viewmodel.details.LaunchDetailsViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchDetailsFragment : Fragment() {
    private lateinit var binding: LaunchDetailsFragmentBinding
    private val args: LaunchDetailsFragmentArgs by navArgs()
    private val viewModel by viewModel<LaunchDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LaunchDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.launchId = args.launchId
        binding.errorView.onRetryClicked = {
            viewModel.performBookOperation()
        }
        binding.bookButton.onClickListener = {
            viewModel.performBookOperation()
        }
        lifecycleScope.launchWhenResumed { viewModel.launchDetailsState().collect(::showLaunchDetails) }
        lifecycleScope.launchWhenResumed { viewModel.errorState().collect(::showError) }
        lifecycleScope.launchWhenResumed { viewModel.loadingState().collect(::showLoading) }
        lifecycleScope.launchWhenResumed { viewModel.buttonLoadingState().collect(::showButtonLoading) }
        lifecycleScope.launchWhenResumed { viewModel.buttonTextState().collect(::showButtonText) }
        lifecycleScope.launchWhenResumed { viewModel.rocketInfoState().collect(::showRocketInfo) }
        lifecycleScope.launchWhenResumed {
            viewModel.navigateEventState().collectEvent {
                findNavController().navigate(R.id.open_login)
            }
        }
    }

    private fun showLaunchDetails(details: LaunchDetails?) {
        details ?: return
        with(binding) {
            missionPatch.load(details.missionPatch) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
            site.text = details.site
            missionName.text = details.missionName
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.isVisible = isLoading
            detailsContentGroup.isVisible = isLoading.not()
        }
    }

    private fun showButtonLoading(isLoading: Boolean) {
        binding.bookButton.isLoading = isLoading
    }

    private fun showError(hasError: Boolean) {
        with(binding) {
            errorView.isVisible = hasError
            progressBar.isVisible = hasError.not()
            detailsContentGroup.isVisible = hasError.not()
        }
    }

    private fun showButtonText(text: String) {
        binding.bookButton.buttonText = text
    }

    private fun showRocketInfo(rocketInfo: String) {
        binding.rocketName.text = rocketInfo
    }
}
