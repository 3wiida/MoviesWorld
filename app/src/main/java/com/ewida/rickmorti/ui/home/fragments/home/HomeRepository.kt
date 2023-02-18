package com.ewida.rickmorti.ui.home.fragments.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import com.ewida.rickmorti.ui.home.fragments.home.adapters.DiscoverMoviesPagingSource
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import com.ewida.rickmorti.utils.result_wrapper.sendSafeApiCall
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiCalls: ApiCalls) {
    suspend fun getDiscoverMovies() = sendSafeApiCall {
        return@sendSafeApiCall Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { DiscoverMoviesPagingSource(apiCalls) }
        )
    }
}
