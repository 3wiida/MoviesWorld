package com.ewida.rickmorti.ui.home.fragments.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.common.Common.ACCOUNT_ID
import com.ewida.rickmorti.common.Common.GENRES_LIST
import com.ewida.rickmorti.common.Keys.MOVIE_ID_KEY
import com.ewida.rickmorti.databinding.FragmentHomeBinding
import com.ewida.rickmorti.model.account_response.AccountResponse
import com.ewida.rickmorti.model.genre_response_model.GenresResponse
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMovieLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMoviesAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated.TopRatedAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated.TopRatedMoviesLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMovieLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMoviesAdapter
import com.ewida.rickmorti.ui.movie.MovieDataActivity
import com.ewida.rickmorti.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    /** Vars **/
    @Inject
    lateinit var bundle: Bundle
    private val discoverMoviesAdapter = DiscoverMoviesAdapter()
    private val trendingMoviesAdapter = TrendingMoviesAdapter()
    private val topRatedMoviesAdapter = TopRatedAdapter()
    private val refreshObserver = MutableLiveData<Boolean>()
    private val mediaType = "movie"
    private val timeWindow = "day"
    override val viewModel: HomeViewModel by viewModels()

    /** Functions **/
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)
    override fun sendCalls() {
        viewModel.getGenreList()
        viewModel.getAccountDetails(requireContext())
    }

    override fun setUpViews() {
        binding.viewModel = viewModel
        initRecyclers()
        observeRefresh()
        viewModel.observeLoading(
            discoverMoviesAdapter,
            trendingMoviesAdapter,
            topRatedMoviesAdapter
        )
    }

    override fun collectResults() {

        collectState<GenresResponse>(
            stateFlow = viewModel.genreList,
            failure = { msg, _ -> GenreListCollector().failure(msg) },
            success = { data -> GenreListCollector().success(data) }
        )

        collectState<AccountResponse>(
            stateFlow = viewModel.accountState,
            failure = { _, _ -> AccountResponseCollector().failure() },
            success = { data -> AccountResponseCollector().success(data) }
        )

    }


    override fun onDestroyView() {
        binding.discoverMovieRv.adapter = null
        binding.trendingMovieRv.adapter = null
        binding.topRatedMoviesRv.adapter = null
        super.onDestroyView()
    }

    private fun initRecyclers() {

        binding.discoverMovieRv.apply {
            adapter = discoverMoviesAdapter.withLoadStateHeaderAndFooter(
                header = DiscoverMovieLoadingStateAdapter(),
                footer = DiscoverMovieLoadingStateAdapter()
            )
            setHasFixedSize(true)
        }

        binding.trendingMovieRv.apply {
            adapter = trendingMoviesAdapter.withLoadStateHeaderAndFooter(
                header = TrendingMovieLoadingStateAdapter(),
                footer = TrendingMovieLoadingStateAdapter()
            )
            setHasFixedSize(true)
        }

        binding.topRatedMoviesRv.apply {
            adapter = topRatedMoviesAdapter.withLoadStateHeaderAndFooter(
                header = TopRatedMoviesLoadingStateAdapter(),
                footer = TopRatedMoviesLoadingStateAdapter()
            )
            setHasFixedSize(true)
        }
    }

    override fun initClicks() {
        discoverMoviesAdapter.onMovieClicked = { movie ->
            bundle.putInt(MOVIE_ID_KEY, movie.id)
            startActivity(Intent(requireActivity(), MovieDataActivity::class.java))
        }

        trendingMoviesAdapter.onMovieClicked = { movie ->
            bundle.putInt(MOVIE_ID_KEY, movie.id)
            startActivity(Intent(requireActivity(), MovieDataActivity::class.java))
        }

        topRatedMoviesAdapter.onMovieClicked = { movie ->
            bundle.putInt(MOVIE_ID_KEY, movie.id)
            startActivity(Intent(requireActivity(), MovieDataActivity::class.java))
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            discoverMoviesAdapter.refresh()
            trendingMoviesAdapter.refresh()
            topRatedMoviesAdapter.refresh()
            refreshObserver.value = true
        }
    }


    private fun observeRefresh() {
        refreshObserver.observe(viewLifecycleOwner) { isRefresh ->
            if (isRefresh) {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.swipeRefreshLayout.isRefreshing = false
                    refreshObserver.value = false
                }, 1500)
            }
        }
    }

    /** Collectors **/
    private fun collectDiscoverMovies() {
        lifecycleScope.launchWhenCreated {
            viewModel.getDiscoverMovies().collectLatest { discoverMoviesAdapter.submitData(it) }
        }
    }

    private fun collectTrendingMovies(mediaType: String, timeWindow: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)
                .collectLatest { trendingMoviesAdapter.submitData(it) }
        }
    }

    private fun collectTopRatedMovies() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTopRatedMovies().collectLatest { topRatedMoviesAdapter.submitData(it) }
        }
    }

    private inner class GenreListCollector {
        fun failure(msg: String) {
            toast(msg)
        }

        fun success(data: GenresResponse) {
            GENRES_LIST = data.genres.toMutableList()
        }
    }

    private inner class AccountResponseCollector {

        fun failure() {
            collectDiscoverMovies()
            collectTopRatedMovies()
            collectTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)
        }

        fun success(data: AccountResponse) {
            binding.me = data
            ACCOUNT_ID = data.id
            collectDiscoverMovies()
            collectTopRatedMovies()
            collectTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)
        }

    }

}