package com.ewida.rickmorti.ui.home.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.databinding.FragmentHomeBinding
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import com.ewida.rickmorti.model.trending_movie_response.TrendingMovies
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMoviesAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    /** Vars **/
    private val discoverMoviesAdapter = DiscoverMoviesAdapter()
    private val trendingMoviesAdapter = TrendingMoviesAdapter()
    private val mediaType = "movie"
    private val timeWindow = "day"
    private var shimmerFlag = true
    override val viewModel: HomeViewModel by viewModels()


    /** Functions **/
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override fun sendCalls() {
        viewModel.getDiscoverMovies()
        viewModel.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)
    }

    override fun setUpViews() {
        initRecyclers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShimmerObservers()
        collectState<Flow<PagingData<DiscoverMovies>>>(
            stateFlow = viewModel.discoverMovieResponse,
            state = Lifecycle.State.CREATED,
            loading = { DiscoverMoviesCollector().loading() },
            failure = { msg, _ -> DiscoverMoviesCollector().failure(msg) },
            success = { lifecycleScope.launch { DiscoverMoviesCollector().success(it) } }
        )

        collectState<Flow<PagingData<TrendingMovies>>>(
            stateFlow = viewModel.trendingMoviesResponse,
            state = Lifecycle.State.CREATED,
            loading = { TrendingMoviesCollector().loading() },
            failure = { msg, _ -> TrendingMoviesCollector().failure(msg) },
            success = { lifecycleScope.launch { TrendingMoviesCollector().success(it) } }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(shimmerFlag){
            binding.discoverMovieShimmer.visibility=View.VISIBLE
            binding.trendingMovieShimmer.visibility=View.VISIBLE
        }else{
            binding.discoverMovieShimmer.visibility=View.INVISIBLE
            binding.trendingMovieShimmer.visibility=View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        binding.discoverMovieRv.adapter = null
        binding.trendingMovieRv.adapter = null
        shimmerFlag=false
        super.onDestroyView()
    }

    private fun initRecyclers() {
        //DiscoverRecycler
        binding.discoverMovieRv.apply {
            adapter = discoverMoviesAdapter
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
            adapter = trendingMoviesAdapter
            layoutManager =
                LinearLayoutManager(
                    binding.trendingMovieRv.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
        }
    }

    private fun initShimmerObservers() {
        discoverMoviesAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.discoverMovieShimmer.hideShimmer()
                binding.discoverMovieShimmer.stopShimmer()
                binding.discoverMovieRv.visibility = View.VISIBLE
                binding.discoverMovieShimmer.visibility = View.INVISIBLE
                discoverMoviesAdapter.unregisterAdapterDataObserver(this)
            }
        })

        trendingMoviesAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.trendingMovieShimmer.hideShimmer()
                binding.trendingMovieShimmer.startShimmer()
                binding.trendingMovieRv.visibility = View.VISIBLE
                binding.trendingMovieShimmer.visibility = View.INVISIBLE
                trendingMoviesAdapter.unregisterAdapterDataObserver(this)
            }
        })
    }


    /** Collectors **/
    private inner class DiscoverMoviesCollector {
        fun loading() {}

        fun failure(msg: String) {
            showToast(requireContext(), msg)
        }

        suspend fun success(data: Flow<PagingData<DiscoverMovies>>) {
            data.collectLatest { list ->
                discoverMoviesAdapter.submitData(list)
            }
        }
    }
    private inner class TrendingMoviesCollector {
        fun loading() {}

        fun failure(msg: String) {
            showToast(requireContext(), msg)
        }

        suspend fun success(data: Flow<PagingData<TrendingMovies>>) {
            data.collectLatest { list ->
                trendingMoviesAdapter.submitData(list)
            }
        }
    }

}