package com.ewida.rickmorti.ui.home.fragments.home

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMoviesAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated.TopRatedAdapter
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMoviesAdapter
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.USER_SESSION_ID
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.getFromPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) : BaseViewModel() {

    /** Vars **/
    private val _accountState = MutableStateFlow<CallState>(CallState.EmptyState)
    val accountState = _accountState.asStateFlow()

    private val _genreList = MutableStateFlow<CallState>(CallState.EmptyState)
    val genreList = _genreList.asStateFlow()

    val isDiscoverLoading = ObservableBoolean(true)
    val isTrendingLoading = ObservableBoolean(true)
    val isTopRatedLoading = ObservableBoolean(true)


    /** Functions **/
    fun getDiscoverMovies() = repo.getDiscoverMovies().flow

    fun getTrendingMovies(mediaType: String, timeWindow: String) =
        repo.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow).flow

    fun getTopRatedMovies() = repo.getTopRatedMovies().flow

    fun getAccountDetails(context: Context) {
        _accountState.value = CallState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repo.getAccountDetails(
                sessionId = getFromPref(
                    context = context,
                    key = USER_SESSION_ID,
                    defValue = ""
                ).toString()
            )) {
                is CallResult.CallFailure -> _accountState.value =
                    CallState.FailureState(response.msg, response.code)
                is CallResult.CallSuccess -> _accountState.value =
                    CallState.SuccessState(response.data)
            }
        }
    }

    fun getGenreList() {
        _genreList.value = CallState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repo.getGenresList()) {
                is CallResult.CallFailure -> _genreList.value =
                    CallState.FailureState(response.msg, response.code)
                is CallResult.CallSuccess -> _genreList.value =
                    CallState.SuccessState(response.data)
            }
        }
    }

    fun observeLoading(
        discoverAdapter: DiscoverMoviesAdapter,
        trendingAdapter: TrendingMoviesAdapter,
        topRatedAdapter: TopRatedAdapter
    ) {
        discoverAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading) isDiscoverLoading.set(false)
        }
        trendingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading) isTrendingLoading.set(false)
        }
        topRatedAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading) isTopRatedLoading.set(false)
        }
    }
}