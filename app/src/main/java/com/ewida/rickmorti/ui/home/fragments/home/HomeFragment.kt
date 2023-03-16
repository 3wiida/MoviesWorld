package com.ewida.rickmorti.ui.home.fragments.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.databinding.FragmentHomeBinding
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMovieLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMoviesAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated.TopRatedAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated.TopRatedMoviesLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMovieLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    /** Vars **/
    private var discoverMoviesDataObserver:RecyclerView.AdapterDataObserver?=null
    private var trendingMoviesDataObserver:RecyclerView.AdapterDataObserver?=null
    private var topRatedMoviesDataObserver:RecyclerView.AdapterDataObserver?=null
    private val discoverMoviesAdapter = DiscoverMoviesAdapter()
    private val trendingMoviesAdapter = TrendingMoviesAdapter()
    private val topRatedMoviesAdapter = TopRatedAdapter()
    private val refreshObserver = MutableLiveData<Boolean>()
    private val mediaType = "movie"
    private val timeWindow = "day"
    override val viewModel: HomeViewModel by viewModels()

    /** Functions **/
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override fun sendCalls() {
        //viewModel.getDiscoverMovies()
        //viewModel.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)
    }

    override fun setUpViews() {
        initRecyclers()
        observeRefresh()
        collectDiscoverMovies()
        collectTopRatedMovies()
        collectTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)

        binding.swipeRefreshLayout.setOnRefreshListener {
            discoverMoviesAdapter.refresh()
            trendingMoviesAdapter.refresh()
            topRatedMoviesAdapter.refresh()
            refreshObserver.value = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShimmerObservers()
    }

    override fun onResume() {
        super.onResume()

        if (discoverMoviesAdapter.snapshot().items.isNotEmpty()) {
            binding.discoverMovieShimmer.hideShimmer()
            binding.discoverMovieShimmer.stopShimmer()
            binding.discoverMovieShimmer.visibility = View.INVISIBLE
        }

        if (trendingMoviesAdapter.snapshot().items.isNotEmpty()) {
            binding.trendingMovieShimmer.hideShimmer()
            binding.trendingMovieShimmer.startShimmer()
            binding.trendingMovieShimmer.visibility = View.INVISIBLE
        }

        if (topRatedMoviesAdapter.snapshot().items.isNotEmpty()) {
            binding.topRatedShimmer.hideShimmer()
            binding.topRatedShimmer.startShimmer()
            binding.topRatedShimmer.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        binding.discoverMovieRv.adapter = null
        binding.trendingMovieRv.adapter = null
        binding.topRatedMoviesRv.adapter = null
        unregisterAdaptersObservers()
        super.onDestroyView()
    }

    private fun initRecyclers() {
        //DiscoverRecycler
        binding.discoverMovieRv.apply {
            adapter = discoverMoviesAdapter.withLoadStateHeaderAndFooter(
                header = DiscoverMovieLoadingStateAdapter(),
                footer = DiscoverMovieLoadingStateAdapter()
            )

            layoutManager =
                LinearLayoutManager(
                    binding.discoverMovieRv.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)

        }

        //TrendingMovies
        binding.trendingMovieRv.apply {
            adapter = trendingMoviesAdapter.withLoadStateHeaderAndFooter(
                header = TrendingMovieLoadingStateAdapter(),
                footer = TrendingMovieLoadingStateAdapter()
            )
            layoutManager =
                LinearLayoutManager(
                    binding.trendingMovieRv.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
        }

        //TopRatedMovies
        binding.topRatedMoviesRv.apply {
            adapter = topRatedMoviesAdapter.withLoadStateHeaderAndFooter(
                header = TopRatedMoviesLoadingStateAdapter(),
                footer = TopRatedMoviesLoadingStateAdapter()
            )
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun initShimmerObservers() {

        discoverMoviesDataObserver = object :RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.discoverMovieShimmer.hideShimmer()
                binding.discoverMovieShimmer.stopShimmer()
                binding.discoverMovieRv.visibility = View.VISIBLE
                binding.discoverMovieShimmer.visibility = View.INVISIBLE
                discoverMoviesDataObserver=null
            }
        }

        trendingMoviesDataObserver=object :RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.trendingMovieShimmer.hideShimmer()
                binding.trendingMovieShimmer.startShimmer()
                binding.trendingMovieRv.visibility = View.VISIBLE
                binding.trendingMovieShimmer.visibility = View.INVISIBLE
                trendingMoviesDataObserver=null

            }
        }

        topRatedMoviesDataObserver=object :RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.topRatedShimmer.hideShimmer()
                binding.topRatedShimmer.startShimmer()
                binding.topRatedShimmer.visibility = View.VISIBLE
                binding.topRatedShimmer.visibility = View.INVISIBLE
                topRatedMoviesDataObserver=null
            }
        }

        registerAdaptersObservers()
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

    private fun registerAdaptersObservers(){
        discoverMoviesDataObserver?.let { discoverMoviesAdapter.registerAdapterDataObserver(it) }
        trendingMoviesDataObserver?.let { trendingMoviesAdapter.registerAdapterDataObserver(it) }
        topRatedMoviesDataObserver?.let { topRatedMoviesAdapter.registerAdapterDataObserver(it) }
    }

    private fun unregisterAdaptersObservers(){
        discoverMoviesDataObserver?.let { discoverMoviesAdapter.unregisterAdapterDataObserver(it) }
        trendingMoviesDataObserver?.let { trendingMoviesAdapter.unregisterAdapterDataObserver(it) }
        topRatedMoviesDataObserver?.let { topRatedMoviesAdapter.unregisterAdapterDataObserver(it) }
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

}