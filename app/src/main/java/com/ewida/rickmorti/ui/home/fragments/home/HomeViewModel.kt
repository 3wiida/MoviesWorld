package com.ewida.rickmorti.ui.home.fragments.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ewida.rickmorti.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) : BaseViewModel() {

    fun getDiscoverMovies() = repo.getDiscoverMovies().flow.cachedIn(viewModelScope)

    fun getTrendingMovies(mediaType: String, timeWindow: String) =
        repo.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow).flow.cachedIn(
            viewModelScope
        )

    fun getTopRatedMovies() = repo.getTopRatedMovies().flow.cachedIn(viewModelScope)

}