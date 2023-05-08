package com.ewida.rickmorti.ui.home.fragments.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.ui.home.fragments.home.adapters.discover.DiscoverMoviesPagingSource
import com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated.TopRatedPagingSource
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMoviesPagingSource
import com.ewida.rickmorti.utils.result_wrapper.sendSafeApiCall
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiCalls: ApiCalls) {
    fun getDiscoverMovies() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 1),
        pagingSourceFactory = { DiscoverMoviesPagingSource(apiCalls = apiCalls) }
    )

    fun getTrendingMovies(mediaType: String, timeWindow: String) = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 1, enablePlaceholders = false),
        pagingSourceFactory = {
            TrendingMoviesPagingSource(
                apiCalls = apiCalls,
                mediaType = mediaType,
                timeWindow = timeWindow
            )
        }
    )

    fun getTopRatedMovies() = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false, prefetchDistance = 1),
        pagingSourceFactory = { TopRatedPagingSource(apiCalls = apiCalls) }
    )

    suspend fun getGenresList() = sendSafeApiCall {
        apiCalls.getMovieType()
    }

    suspend fun getAccountDetails(sessionId: String) =
        sendSafeApiCall { apiCalls.getAccountDetails(session_id = sessionId) }
}
