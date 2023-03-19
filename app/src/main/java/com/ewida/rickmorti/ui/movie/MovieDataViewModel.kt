package com.ewida.rickmorti.ui.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.common.Common
import com.ewida.rickmorti.common.Common.EMPTY_STRING
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.model.movie_response_model.Genre
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDataViewModel @Inject constructor(private val repo:MovieDataRepository):BaseViewModel() {

    /** VARS **/
    private val _movieStateFlow = MutableStateFlow<CallState>(CallState.EmptyState)
    val movieStateFlow = _movieStateFlow.asStateFlow()

    private val _castStateFlow = MutableStateFlow<CallState>(CallState.EmptyState)
    val castStateFlow = _castStateFlow.asStateFlow()

    /** FUNCTIONS **/
    fun getMovieById(movieId:Int){
        viewModelScope.launch {
            _movieStateFlow.value=CallState.LoadingState
            when(val response=repo.getMovieById(movieId)){
                is CallResult.CallFailure -> _movieStateFlow.value=CallState.FailureState(response.msg,response.code)
                is CallResult.CallSuccess -> _movieStateFlow.value=CallState.SuccessState(response.data)
            }
        }
    }

    fun getMovieCast(movieId:Int){
        viewModelScope.launch {
            _castStateFlow.value=CallState.LoadingState
            when(val response=repo.getMovieCast(movieId)){
                is CallResult.CallFailure -> _castStateFlow.value=CallState.FailureState(response.msg,response.code)
                is CallResult.CallSuccess -> _castStateFlow.value=CallState.SuccessState(response.data)
            }
        }
    }

}