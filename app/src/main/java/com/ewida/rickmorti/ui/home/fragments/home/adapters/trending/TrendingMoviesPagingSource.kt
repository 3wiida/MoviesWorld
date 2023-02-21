package com.ewida.rickmorti.ui.home.fragments.home.adapters.trending

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.model.trending_movie_response.TrendingMovies

import javax.inject.Inject

class TrendingMoviesPagingSource @Inject constructor(
    private val apiCalls: ApiCalls,
    private val mediaType: String,
    private val timeWindow: String
) : PagingSource<Int, TrendingMovies>() {
    override fun getRefreshKey(state: PagingState<Int, TrendingMovies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingMovies> {
        val pageIndex = params.key ?: 1
        return try {
            val response = apiCalls.getTrendingMovies(
                mediaType = mediaType,
                timeWindow = timeWindow,
                page = pageIndex
            )
            val prevPage = if (pageIndex==1) null else pageIndex - 1
            val nextPage = if (response.results.isEmpty()) null else pageIndex + 1
            LoadResult.Page(
                data = response.results,
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }
}