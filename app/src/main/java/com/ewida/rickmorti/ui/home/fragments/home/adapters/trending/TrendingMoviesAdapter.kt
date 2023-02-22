package com.ewida.rickmorti.ui.home.fragments.home.adapters.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.common.Common.EMPTY_STRING
import com.ewida.rickmorti.common.Common.IMAGE_URL
import com.ewida.rickmorti.common.Keys.YEAR
import com.ewida.rickmorti.databinding.TrendingMovieItemBinding
import com.ewida.rickmorti.model.trending_movie_response.TrendingMovies
import com.ewida.rickmorti.utils.bindImageUrl
import com.ewida.rickmorti.utils.date_time_utils.DateTimeUtils.getDateDetails
import java.util.Locale

class TrendingMoviesAdapter :
    PagingDataAdapter<TrendingMovies, TrendingMoviesAdapter.TrendingMovieViewHolder>(Comparator) {

    val datePattern = "yyyy-MM-dd"
    val localeLanguage="en"
    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder {
        val binding =
            TrendingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingMovieViewHolder(binding)
    }

    inner class TrendingMovieViewHolder(private val binding: TrendingMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TrendingMovies) {
            binding.movieImage.bindImageUrl(IMAGE_URL + item.poster_path)
            binding.movieName.text = item.title
            binding.movieReleasedYear.text = getReleasedDateYear(
                date = item.release_date,
                pattern = datePattern,
                local = Locale(localeLanguage)
            )?:EMPTY_STRING
        }
    }

    private object Comparator : DiffUtil.ItemCallback<TrendingMovies>() {
        override fun areItemsTheSame(oldItem: TrendingMovies, newItem: TrendingMovies) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TrendingMovies, newItem: TrendingMovies) =
            oldItem == newItem

    }

    private fun getReleasedDateYear(date: String, pattern: String, local: Locale): String? {
        val dateDetails = getDateDetails(date = date, pattern = pattern, local = local)
        return "(${dateDetails[YEAR]})"
    }
}