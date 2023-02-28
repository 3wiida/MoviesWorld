package com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.common.Common.DATE_PATTERN
import com.ewida.rickmorti.common.Common.EMPTY_STRING
import com.ewida.rickmorti.common.Common.IMAGE_URL
import com.ewida.rickmorti.common.Keys.YEAR
import com.ewida.rickmorti.databinding.TopRatedMovieItemBinding
import com.ewida.rickmorti.model.top_rated_response.TopRatedMovie
import com.ewida.rickmorti.utils.bindImageUrl
import com.ewida.rickmorti.utils.date_time_utils.DateTimeUtils.getDateDetails

class TopRatedAdapter :
    PagingDataAdapter<TopRatedMovie, TopRatedAdapter.MovieViewHolder>(Comparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { movie ->
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding=TopRatedMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }

    private fun getReleasedDateYear(date: String, pattern: String): String {
        val dateDetails = getDateDetails(date = date, pattern = pattern)
        return dateDetails[YEAR]?: EMPTY_STRING
    }

    inner class MovieViewHolder(private val binding: TopRatedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TopRatedMovie) {
            binding.movieImage.setImage(IMAGE_URL+item.poster_path)
            binding.movieName.text = item.title
            binding.movieRate.text = item.vote_average.toString()
            binding.movieReleasedYear.text = getReleasedDateYear(
                date = item.release_date,
                pattern = DATE_PATTERN
            )
        }
    }

    private object Comparator : DiffUtil.ItemCallback<TopRatedMovie>() {
        override fun areItemsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TopRatedMovie, newItem: TopRatedMovie) =
            oldItem.id == newItem.id
    }
}