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
import com.ewida.rickmorti.ui.home.fragments.home.adapters.DiscoverMoviesAdapter
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
    private var isFirstPageLoaded = false

    /** Functions **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initShimmerObservers()
        networkObserver = NetworkObserver(requireContext())
        collectState<Flow<PagingData<DiscoverMovies>>>(
            viewModel.discoverMovieResponse,
            Lifecycle.State.RESUMED,
            { DiscoverMoviesCollector().loading() },
            { msg, _ -> DiscoverMoviesCollector().failure(msg) },
            { lifecycleScope.launch { DiscoverMoviesCollector().success(it) } }
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

    }
    private fun initShimmerObservers() {
        discoverMoviesAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.initialShimmerLayout.hideShimmer()
                binding.initialShimmerLayout.stopShimmer()
                binding.discoverMovieRv.visibility = View.VISIBLE
                binding.initialShimmerLayout.visibility = View.GONE
                discoverMoviesAdapter.unregisterAdapterDataObserver(this)
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
            binding.initialShimmerLayout.startShimmer()
        }

        fun failure(msg: String) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        suspend fun success(data: Flow<PagingData<DiscoverMovies>>) {
            data.collectLatest { list ->
                discoverMoviesAdapter.submitData(list)
            }
        }

    }
}