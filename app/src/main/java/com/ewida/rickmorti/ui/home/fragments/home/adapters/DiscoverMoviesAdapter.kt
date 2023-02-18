package com.ewida.rickmorti.ui.home.fragments.home.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.IMAGE_URL
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.databinding.DiscoverMovieItemBinding
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

class DiscoverMoviesAdapter:PagingDataAdapter<DiscoverMovies,DiscoverMoviesAdapter.ViewHolder>(MovieComparator) {

    private val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1800)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .setFixedHeight(180)
        .setFixedWidth(250)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }


    inner class ViewHolder(val binding:DiscoverMovieItemBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(item:DiscoverMovies){
            val requestBuilder=Glide.with(binding.moviePhoto).asDrawable().sizeMultiplier(0.05f)
            Glide.with(binding.shimmerImage).load(IMAGE_URL+item.poster_path).placeholder(shimmerDrawable).thumbnail(requestBuilder)
                .transition(withCrossFade()).into(binding.shimmerImage)

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