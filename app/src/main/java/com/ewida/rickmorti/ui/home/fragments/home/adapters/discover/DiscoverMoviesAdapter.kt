package com.ewida.rickmorti.ui.home.fragments.home.adapters.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.DiscoverMovieItemBinding
import com.ewida.rickmorti.model.common_movie_response.CommonMovie
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies

class DiscoverMoviesAdapter:PagingDataAdapter<CommonMovie, DiscoverMoviesAdapter.ViewHolder>(
    MovieComparator
) {
    var onMovieClicked:((CommonMovie)->Unit)?=null
    inner class ViewHolder(val binding:DiscoverMovieItemBinding):RecyclerView.ViewHolder(binding.root)

    private object MovieComparator:DiffUtil.ItemCallback<CommonMovie>(){
        override fun areItemsTheSame(oldItem: CommonMovie, newItem: CommonMovie): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CommonMovie, newItem: CommonMovie): Boolean {
            return oldItem==newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        holder.binding.movie=item
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            item?.let { movie->
                onMovieClicked?.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DiscoverMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

}