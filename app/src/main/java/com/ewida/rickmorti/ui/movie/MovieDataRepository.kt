package com.ewida.rickmorti.ui.movie

import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.utils.result_wrapper.sendSafeApiCall
import javax.inject.Inject


class MovieDataRepository @Inject constructor(private val apiCalls: ApiCalls) {
    suspend fun getMovieById(movieId: Int) = sendSafeApiCall { apiCalls.getMovieById(movieId) }
    suspend fun getMovieCast(movieId: Int) = sendSafeApiCall { apiCalls.getMovieCast(movieId) }
}