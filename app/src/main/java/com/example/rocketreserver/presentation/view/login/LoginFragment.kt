package com.example.rocketreserver.presentation.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LoginFragmentBinding
import com.example.rocketreserver.presentation.ext.collectEvent
import com.example.rocketreserver.presentation.viewmodel.login.LoginViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitButton.apply {
            buttonText = resources.getString(R.string.submit)
            onClickListener = {
                viewModel.performLogin(binding.email.text?.toString().orEmpty())
            }
        }
        lifecycleScope.launchWhenResumed { viewModel.errorTextState().collect(::showErrorText) }
        lifecycleScope.launchWhenResumed { viewModel.errorState().collect(::showError) }
        lifecycleScope.launchWhenResumed { viewModel.buttonLoadingState().collect(::showButtonLoading) }
        lifecycleScope.launchWhenResumed {
            viewModel.navigateEventState().collectEvent {
                findNavController().popBackStack()
            }
        }
    }

    private fun showErrorText(errorText: String) {
        binding.emailLayout.error = errorText
    }

    private fun showError(hasError: Boolean) {
        with(binding) {
            errorView.isVisible = hasError
            contentGroup.isVisible = hasError.not()
        }
    }

    private fun showButtonLoading(isLoading: Boolean) {
        binding.submitButton.isLoading = isLoading
    }
}