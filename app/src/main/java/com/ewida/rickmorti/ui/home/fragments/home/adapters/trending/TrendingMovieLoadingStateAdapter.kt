package com.ewida.rickmorti.ui.home.fragments.home.adapters.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.TrendingLoadingStateLayoutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TrendingMovieLoadingStateAdapter:LoadStateAdapter<TrendingMovieLoadingStateAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(val binding:TrendingLoadingStateLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState){
            binding.trendingMovieShimmer.isVisible= loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieViewHolder {
        return MovieViewHolder(TrendingLoadingStateLayoutBinding.bind(LayoutInflater.from(parent.context).inflate(
            R.layout.trending_loading_state_layout,parent,false)))
    }
}