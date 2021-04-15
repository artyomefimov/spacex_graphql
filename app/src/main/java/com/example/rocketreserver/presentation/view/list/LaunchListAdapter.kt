package com.example.rocketreserver.presentation.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LaunchItemBinding
import com.example.rocketreserver.domain.model.LaunchList
import com.example.rocketreserver.domain.model.LaunchListElement

class LaunchListAdapter(
    private val launches: MutableList<LaunchListElement> = mutableListOf(),
    private val onEndOfListReached: () -> Unit,
    private val onItemClicked: (LaunchListElement) -> Unit
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

    fun swapData(items: List<LaunchListElement>) {
        launches.clear()
        launches.addAll(items)
        notifyDataSetChanged()
    }
}

class LaunchListViewHolder(
    private val binding: LaunchItemBinding,
    private val onItemClicked: (LaunchListElement) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launch: LaunchListElement) {
        with(binding) {
            site.text = launch.site
            missionName.text = launch.missionName
            missionPatch.load(launch.missionPatch) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
            root.setOnClickListener { onItemClicked(launch) }
        }
    }
}