package com.ewida.rickmorti.ui.home.fragments.home

import android.telecom.Call
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) : BaseViewModel() {

    /** Vars **/
    private val _discoverMoviesResponse = MutableStateFlow<CallState>(CallState.EmptyState)
    val discoverMovieResponse = _discoverMoviesResponse.asStateFlow()

    private val _trendingMoviesResponse = MutableStateFlow<CallState>(CallState.EmptyState)
    val trendingMoviesResponse = _trendingMoviesResponse.asStateFlow()

    /** Functions **/
    fun getDiscoverMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _discoverMoviesResponse.value = CallState.LoadingState
            when (val response = repo.getDiscoverMovies()) {
                is CallResult.CallFailure -> _discoverMoviesResponse.value =
                    CallState.FailureState(msg = response.msg, code = response.code)
                is CallResult.CallSuccess -> {
                    val data = response.data.flow
                    _discoverMoviesResponse.value = CallState.SuccessState(data)
                }
            }
        }
    }

    fun getTrendingMovies(mediaType: String, timeWindow: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _discoverMoviesResponse.value = CallState.LoadingState
            when (val response =
                repo.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow)) {
                is CallResult.CallFailure -> _trendingMoviesResponse.value =
                    CallState.FailureState(msg = response.msg, code = response.code)
                is CallResult.CallSuccess -> {
                    val data = response.data.flow
                    _trendingMoviesResponse.value = CallState.SuccessState(data = data)
                }
            }
        }
    }

}