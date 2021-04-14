package com.example.rocketreserver.presentation.view.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.rocketreserver.LaunchListQuery
import com.example.rocketreserver.data.api.apolloClient
import com.example.rocketreserver.databinding.LaunchListFragmentBinding
import com.example.rocketreserver.domain.interactor.list.LaunchesListInteractor
import com.example.rocketreserver.domain.model.result.ResultWrapper
import kotlinx.coroutines.channels.Channel
import org.koin.android.ext.android.inject

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding
    private val channel = Channel<Unit>(Channel.CONFLATED)
    private var adapter: LaunchListAdapter? = null
    var cursor: String? = null
    val interactor: LaunchesListInteractor by inject<LaunchesListInteractor>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = LaunchListAdapter(
            launches = mutableListOf(),
            onEndOfListReached = {
                if (channel.isClosedForSend.not()) {
                    channel.offer(Unit)
                }
            },
            onItemClicked = {
                findNavController().navigate(
                    LaunchListFragmentDirections.openLaunchDetails(launchId = it.id)
                )
            }
        )
        binding.launchesRecyclerView.adapter = adapter

        lifecycleScope.launchWhenResumed {
            val result = interactor.getLaunches(cursor)
            if (result.isSuccess) {
                adapter?.launches?.addAll((result as ResultWrapper.Success).data.launches)
                adapter?.notifyDataSetChanged()
            }
//            val response = try {
//                apolloClient(requireContext()).query(LaunchListQuery(cursor = Input.fromNullable(cursor))).await()
//            } catch (e: ApolloException) {
//                Log.d("LaunchList", "Failure", e)
//                return@launchWhenResumed
//            }
//            response.data?.launches?.launches?.filterNotNull()?.let {
//                adapter?.launches?.addAll(it)
//                adapter?.notifyDataSetChanged()
//            }
        }

        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                val result = interactor.getLaunches(cursor)
                if (result.isSuccess) {
                    adapter?.launches?.addAll((result as ResultWrapper.Success).data.launches)
                    adapter?.notifyDataSetChanged()
                }
                val success = result as ResultWrapper.Success
                cursor = success.data.cursor
                if (success.data.hasMore.not()) {
                    break
                }
            }

            channel.close()
        }
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}