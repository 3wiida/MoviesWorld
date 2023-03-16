package com.ewida.rickmorti.ui.home.fragments.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.SearchResultItemBinding
import com.ewida.rickmorti.model.common_movie_response.CommonMovie

class SearchResultsAdapter:PagingDataAdapter<CommonMovie,SearchResultsAdapter.MovieViewHolder>(Comparator) {

    inner class MovieViewHolder(val binding:SearchResultItemBinding):RecyclerView.ViewHolder(binding.root)

    private object Comparator:DiffUtil.ItemCallback<CommonMovie>(){
        override fun areItemsTheSame(oldItem: CommonMovie, newItem: CommonMovie)=oldItem==newItem
        override fun areContentsTheSame(oldItem: CommonMovie, newItem: CommonMovie)=oldItem.id==newItem.id
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { movie->
            holder.binding.movie=movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding=SearchResultItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieViewHolder(binding)
    }
}