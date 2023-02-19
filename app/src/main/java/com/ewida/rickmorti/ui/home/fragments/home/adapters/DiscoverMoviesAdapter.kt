package com.ewida.rickmorti.ui.home.fragments.home.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ewida.rickmorti.common.Common.IMAGE_URL
import com.ewida.rickmorti.databinding.DiscoverMovieItemBinding
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DiscoverMoviesAdapter:PagingDataAdapter<DiscoverMovies,DiscoverMoviesAdapter.ViewHolder>(MovieComparator) {

    inner class ViewHolder(val binding:DiscoverMovieItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:DiscoverMovies){
            val requestBuilder=Glide.with(binding.movieImage).asDrawable().sizeMultiplier(0.05f)
            Glide.with(binding.movieImage).load(IMAGE_URL+item.poster_path).thumbnail(requestBuilder)
                .transition(withCrossFade()).into(binding.movieImage)

        }
    }

    private object MovieComparator:DiffUtil.ItemCallback<DiscoverMovies>(){
        override fun areItemsTheSame(oldItem: DiscoverMovies, newItem: DiscoverMovies): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: DiscoverMovies, newItem: DiscoverMovies): Boolean {
            return oldItem==newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        item?.let {
            holder.bind(item = item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DiscoverMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

}