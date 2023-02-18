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
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.databinding.FragmentHomeBinding
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import com.ewida.rickmorti.ui.home.fragments.home.adapters.DiscoverMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    /** Vars **/
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val discoverMoviesAdapter = DiscoverMoviesAdapter()

    /** Functions **/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecyclers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDiscoverMovies()
        collectState<Flow<PagingData<DiscoverMovies>>>(
            viewModel.discoverMovieResponse,
            Lifecycle.State.CREATED,
            { DiscoverMoviesCollector().loading() },
            { msg, _ -> DiscoverMoviesCollector().failure(msg) },
            {
                lifecycleScope.launchWhenCreated {
                    DiscoverMoviesCollector().success(it)
                }
            }
        )

    }

    private fun initRecyclers(){
        //DiscoverRecycler
        binding.discoverMovieRv.apply {
            adapter=discoverMoviesAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }


    private inner class DiscoverMoviesCollector {
        fun loading() {
            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
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