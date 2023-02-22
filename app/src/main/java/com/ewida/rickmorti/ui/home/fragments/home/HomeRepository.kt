package com.ewida.rickmorti.ui.home.fragments.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMoviesPagingSource
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMoviesPagingSource
import com.ewida.rickmorti.utils.result_wrapper.sendSafeApiCall

import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiCalls: ApiCalls) {
    suspend fun getDiscoverMovies() = sendSafeApiCall {
        return@sendSafeApiCall Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 1),
            pagingSourceFactory = { DiscoverMoviesPagingSource(apiCalls = apiCalls) }
        )
    }

    suspend fun getTrendingMovies(mediaType: String, timeWindow: String) = sendSafeApiCall {
        return@sendSafeApiCall Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1, enablePlaceholders = false),
            pagingSourceFactory = {
                TrendingMoviesPagingSource(
                    apiCalls = apiCalls,
                    mediaType = mediaType,
                    timeWindow = timeWindow
                )
            }
        )
    }

}
