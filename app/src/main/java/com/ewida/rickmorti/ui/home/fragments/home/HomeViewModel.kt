package com.ewida.rickmorti.ui.home.fragments.home

import android.telecom.Call
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepository) : BaseViewModel() {

    /** Vars **/
    private val _genreList=MutableStateFlow<CallState>(CallState.EmptyState)
    val genreList=_genreList.asStateFlow()

    /** Functions **/
    fun getDiscoverMovies() = repo.getDiscoverMovies().flow

    fun getTrendingMovies(mediaType: String, timeWindow: String) =
        repo.getTrendingMovies(mediaType = mediaType, timeWindow = timeWindow).flow

    fun getTopRatedMovies() = repo.getTopRatedMovies().flow

    fun getGenreList(){
        _genreList.value=CallState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = repo.getGenresList()){
                is CallResult.CallFailure -> _genreList.value=CallState.FailureState(response.msg,response.code)
                is CallResult.CallSuccess -> _genreList.value=CallState.SuccessState(response.data)
            }
        }
    }

}