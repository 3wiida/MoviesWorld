package com.ewida.rickmorti.ui.home.fragments.home.adapters.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.DiscoverLoadingStateLayoutBinding

class DiscoverMovieLoadingStateAdapter : LoadStateAdapter<DiscoverMovieLoadingStateAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding:DiscoverLoadingStateLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.discoverMovieShimmer.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return ItemViewHolder(
            DiscoverLoadingStateLayoutBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.discover_loading_state_layout, parent, false
                )
            )
        )
    }
}

