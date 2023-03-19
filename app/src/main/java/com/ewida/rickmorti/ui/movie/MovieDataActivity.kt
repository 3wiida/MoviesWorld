package com.ewida.rickmorti.ui.movie

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewida.rickmorti.base.BaseActivity
import com.ewida.rickmorti.common.Keys.MOVIE_ID_KEY
import com.ewida.rickmorti.databinding.ActivityMovieDataBinding
import com.ewida.rickmorti.model.cast_response_model.CastResponse
import com.ewida.rickmorti.model.movie_response_model.MovieResponse
import com.ewida.rickmorti.ui.movie.adapters.CastAdapter
import com.ewida.rickmorti.utils.date_time_utils.DateTimeUtils.getMovieDuration
import com.ewida.rickmorti.utils.recycler_decoration.CastRecyclerSpacing
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDataActivity : BaseActivity<ActivityMovieDataBinding, MovieDataViewModel>() {
    @Inject lateinit var bundle:Bundle
    private val castAdapter=CastAdapter()
    override val viewModel: MovieDataViewModel by viewModels()
    override fun getViewBinding() = ActivityMovieDataBinding.inflate(layoutInflater)

    override fun sendCalls() {
        val movieId:Int=bundle.getInt(MOVIE_ID_KEY)
        viewModel.getMovieById(movieId)
        viewModel.getMovieCast(movieId)
        collectState<MovieResponse>(
            stateFlow = viewModel.movieStateFlow,
            state = Lifecycle.State.CREATED,
            loading = {MovieResponseCollector().callLoading()},
            failure = {msg,_->MovieResponseCollector().callFailure(msg)},
            success = {data->MovieResponseCollector().callSuccess(data)}
        )
        collectState<CastResponse>(
            stateFlow = viewModel.castStateFlow,
            state = Lifecycle.State.CREATED,
            loading = {CastResponseCollector().callLoading()},
            failure = {msg,_->CastResponseCollector().callFailure(msg)},
            success = {data->CastResponseCollector().callSuccess(data)}
        )
    }

    override fun setUpViews() {
        lifecycle.addObserver(binding.movieVideoPlayer)
        controlVideoPlayer()
        initRecyclers()
        binding.movieName.isSelected=true
    }

    private fun initRecyclers(){
        binding.castRecycler.apply {
            layoutManager = LinearLayoutManager(this@MovieDataActivity,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(CastRecyclerSpacing())
            adapter = castAdapter
        }

    }

    private fun controlVideoPlayer() {
        binding.movieVideoPlayer.initialize(
            provideVideoPlayerListener(),
            true,
            provideIFrameOptions()
        )
    }

    private fun provideVideoPlayerListener(): YouTubePlayerListener {
        return object : YouTubePlayerListener {
            override fun onApiChange(youTubePlayer: YouTubePlayer) {}
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}
            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError
            ) {}
            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {}
            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {}
            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {}

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer,
                loadedFraction: Float
            ) {}

            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle,"XZ8daibM3AE",0f)
            }

        }
    }

    private fun provideIFrameOptions(): IFramePlayerOptions {
        return IFramePlayerOptions.Builder().controls(0).build()
    }

    /** Collectors **/
    inner class MovieResponseCollector{
        fun callLoading(){binding.isLoading?.set(true)}
        fun callFailure(msg:String){
            binding.isLoading?.set(false)
            showToast(this@MovieDataActivity,msg)
        }
        fun callSuccess(response:MovieResponse){
            binding.isLoading?.set(false)
            binding.movie=response
        }
    }

    inner class CastResponseCollector{
        fun callLoading(){binding.isLoading?.set(true)}
        fun callFailure(msg:String){
            binding.isLoading?.set(false)
            showToast(this@MovieDataActivity,msg)
        }
        fun callSuccess(response:CastResponse){
            castAdapter.submitList(response.cast)
        }
    }

}