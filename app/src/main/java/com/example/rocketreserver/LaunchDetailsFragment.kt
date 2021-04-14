package com.example.rocketreserver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.rocketreserver.data.api.apolloClient
import com.example.rocketreserver.databinding.LaunchDetailsFragmentBinding

class LaunchDetailsFragment : Fragment() {

    private lateinit var binding: LaunchDetailsFragmentBinding
    val args: LaunchDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchDetailsFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            binding.bookButton.isVisible = false
            binding.bookProgressBar.isVisible = false
            binding.progressBar.isVisible = true
            binding.error.isVisible = false
            runCatching {
                apolloClient(requireContext()).query(LaunchDetailsQuery(id = args.launchId)).await()
            }.onSuccess { response ->
                val launch = response.data?.launch
                if (launch == null || response.hasErrors()) {
                    binding.progressBar.isVisible = false
                    binding.error.text = response.errors?.get(0)?.message
                    binding.error.isVisible = true
                    return@launchWhenResumed
                }

                binding.progressBar.isVisible = false

                binding.missionPatch.load(launch.mission?.missionPatch) {
                    placeholder(R.drawable.ic_placeholder)
                }
                binding.site.text = launch.site
                binding.missionName.text = launch.mission?.name
                val rocket = launch.rocket
                binding.rocketName.text = "🚀 ${rocket?.name} ${rocket?.type}"

                val mutation = if (launch.isBooked) {
                    CancelTripMutation(id = args.launchId)
                } else {
                    BookTripMutation(id = args.launchId)
                }
                val bookingResponse = try {
                    apolloClient(requireContext()).mutate(mutation).await()
                } catch (e: ApolloException) {
                    configureButton(launch.isBooked)
                    return@launchWhenResumed
                }

                if (bookingResponse.hasErrors()) {
                    configureButton(launch.isBooked)
                    return@launchWhenResumed
                }
                configureButton(launch.isBooked.not())
            }.onFailure {
                if (it is ApolloException) {
                    binding.progressBar.visibility = View.GONE
                    binding.error.text = "Oh no... A protocol error happened"
                    binding.error.visibility = View.VISIBLE
                    return@launchWhenResumed
                }
            }
        }
    }

    private fun configureButton(isBooked: Boolean) {
        binding.bookButton.visibility = View.VISIBLE
        binding.bookProgressBar.visibility = View.GONE

        binding.bookButton.text = if (isBooked) {
            getString(R.string.cancel)
        } else {
            getString(R.string.book_now)
        }

        binding.bookButton.setOnClickListener {
            val context = context
            if (context != null && User.getToken(context) == null) {
                findNavController().navigate(
                    R.id.open_login
                )
                return@setOnClickListener
            }
        }
    }
}