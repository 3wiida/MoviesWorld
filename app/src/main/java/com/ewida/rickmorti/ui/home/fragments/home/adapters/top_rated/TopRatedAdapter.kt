package com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.TopRatedMovieItemBinding
import com.ewida.rickmorti.model.common_movie_response.CommonMovie
import com.ewida.rickmorti.model.top_rated_response.TopRatedMovie

class TopRatedAdapter :
    PagingDataAdapter<CommonMovie, TopRatedAdapter.MovieViewHolder>(Comparator) {

    var onMovieClicked:((CommonMovie)->Unit)?=null
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { movie ->
            holder.binding.movie=movie
            holder.binding.apply {
                movieName.isSelected=true
            }
            holder.itemView.setOnClickListener {
                onMovieClicked?.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding=TopRatedMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(val binding: TopRatedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object Comparator : DiffUtil.ItemCallback<CommonMovie>() {
        override fun areItemsTheSame(oldItem: CommonMovie, newItem: CommonMovie) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CommonMovie, newItem: CommonMovie) =
            oldItem.id == newItem.id
    }
}