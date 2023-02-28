package com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.TopRatedLoadingStateBinding

class TopRatedMoviesLoadingStateAdapter :
    LoadStateAdapter<TopRatedMoviesLoadingStateAdapter.MovieViewHolder>() {

    override fun onBindViewHolder(holder: MovieViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieViewHolder {
        val binding = TopRatedLoadingStateBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.top_rated_loading_state, parent, false)
        )
        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(private val binding: TopRatedLoadingStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.topRatedShimmer.isVisible = loadState is LoadState.Loading
        }
    }
}