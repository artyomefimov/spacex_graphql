package com.example.rocketreserver.presentation.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rocketreserver.LaunchListQuery
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LaunchItemBinding

class LaunchListAdapter(
    val launches: MutableList<LaunchListQuery.Launch>,
    private val onEndOfListReached: () -> Unit,
    private val onItemClicked: (LaunchListQuery.Launch) -> Unit
) : RecyclerView.Adapter<LaunchListViewHolder>() {

    override fun getItemCount() = launches.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchListViewHolder {
        val binding = LaunchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchListViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: LaunchListViewHolder, position: Int) {
        holder.bind(launches[position])
        if (position == launches.size - 1) {
            onEndOfListReached()
        }
    }
}

class LaunchListViewHolder(
    private val binding: LaunchItemBinding,
    private val onItemClicked: (LaunchListQuery.Launch) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launch: LaunchListQuery.Launch) {
        with(binding) {
            site.text = launch.site.orEmpty()
            missionName.text = launch.mission?.name.orEmpty()
            missionPatch.load(launch.mission?.missionPatch.orEmpty()) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
            root.setOnClickListener { onItemClicked(launch) }
        }
    }
}