package com.ewida.rickmorti.ui.home.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.R
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
class HomeFragment : BaseFragment() {

    /** Vars **/
    private lateinit var binding: FragmentHomeBinding
    private lateinit var networkObserver: NetworkObserver
    private val viewModel: HomeViewModel by viewModels()
    private val discoverMoviesAdapter = DiscoverMoviesAdapter()
    private val trendingMoviesAdapter = TrendingMoviesAdapter()
    private var isFirstPageLoaded = false
    private val mediaType="movie"
    private val timeWindow="day"

    /** Functions **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShimmerObservers()
        networkObserver = NetworkObserver(requireContext())
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
            loading = {TrendingMoviesCollector().loading()},
            failure = {msg,_->TrendingMoviesCollector().failure(msg)},
            success = {lifecycleScope.launch { TrendingMoviesCollector().success(it) }}
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecyclers()
        initNetworkObserver()
        return binding.root
    }

    private fun initRecyclers() {
        //DiscoverRecycler
        binding.discoverMovieRv.apply {
            adapter = discoverMoviesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        //TrendingMovies
        binding.trendingMovieRv.apply {
            adapter=trendingMoviesAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
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

        trendingMoviesAdapter.registerAdapterDataObserver(object :RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.trendingMovieShimmer.hideShimmer()
                binding.trendingMovieShimmer.startShimmer()
                binding.trendingMovieRv.visibility=View.VISIBLE
                binding.trendingMovieShimmer.visibility=View.INVISIBLE
                trendingMoviesAdapter.unregisterAdapterDataObserver(this)
            }
        })
    }

    private fun initNetworkObserver() {
        lifecycleScope.launch {
            networkObserver.observe().collectLatest { state->
                when (state) {
                    NetworkObserver.Status.Available -> {
                        if (!isFirstPageLoaded) {
                            viewModel.getDiscoverMovies()
                            viewModel.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)
                            isFirstPageLoaded = true
                        }
                    }
                    NetworkObserver.Status.Unavailable -> showToast(requireContext(),getString(R.string.networkProblem))
                    NetworkObserver.Status.Lost -> showToast(requireContext(),getString(R.string.networkProblem))
                    NetworkObserver.Status.Losing -> {}
                }
            }
        }
    }

    /** Collectors **/
    private inner class DiscoverMoviesCollector {
        fun loading() {
            binding.discoverMovieShimmer.startShimmer()
        }
        fun failure(msg: String) {
            showToast(requireContext(),msg)
        }
        suspend fun success(data: Flow<PagingData<DiscoverMovies>>) {
            data.collectLatest { list ->
                discoverMoviesAdapter.submitData(list)
            }
        }
    }
    private inner class TrendingMoviesCollector{
        fun loading(){
            binding.trendingMovieShimmer.startShimmer()
        }
        fun failure(msg: String){
            showToast(requireContext(),msg)
        }
        suspend fun success(data:Flow<PagingData<TrendingMovies>>){
            data.collectLatest {list->
                trendingMoviesAdapter.submitData(list)
            }
        }
    }
}