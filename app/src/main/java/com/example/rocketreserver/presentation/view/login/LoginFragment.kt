package com.example.rocketreserver.presentation.view.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitProgressBar.visibility = View.GONE
        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
                binding.emailLayout.error = getString(R.string.invalid_email)
                return@setOnClickListener
            }
            binding.submitProgressBar.visibility = View.VISIBLE
            binding.submit.visibility = View.GONE
//            lifecycleScope.launchWhenResumed {
//                val response = try {
//                    apolloClient(requireContext()).mutate(LoginMutation(email = Input.fromNullable(email))).await()
//                } catch (e: Exception) {
//                    null
//                }
//
//                val login = response?.data?.login
//                if (login == null || response.hasErrors()) {
//                    binding.submitProgressBar.visibility = View.GONE
//                    binding.submit.visibility = View.VISIBLE
//                    return@launchWhenResumed
//                }
//
//                User.setToken(requireContext(), login)
//                findNavController().popBackStack()
//            }
        }
    }
}