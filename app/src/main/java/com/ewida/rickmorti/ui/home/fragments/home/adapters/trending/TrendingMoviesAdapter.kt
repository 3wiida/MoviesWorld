package com.ewida.rickmorti.ui.home.fragments.home.adapters.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.TrendingMovieItemBinding
import com.ewida.rickmorti.model.common_movie_response.CommonMovie

class TrendingMoviesAdapter :
    PagingDataAdapter<CommonMovie, TrendingMoviesAdapter.TrendingMovieViewHolder>(Comparator) {
    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.binding.movie=movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        val binding =
            TrendingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingMovieViewHolder(binding)
    }

    inner class TrendingMovieViewHolder(val binding: TrendingMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object Comparator : DiffUtil.ItemCallback<CommonMovie>() {
        override fun areItemsTheSame(oldItem: CommonMovie, newItem: CommonMovie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CommonMovie, newItem: CommonMovie) =
            oldItem == newItem

    }

}