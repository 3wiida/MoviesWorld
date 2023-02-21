package com.ewida.rickmorti.ui.home.fragments.home.adapters.discover

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import javax.inject.Inject

class DiscoverMoviesPagingSource @Inject constructor(private val apiCalls: ApiCalls) :
    PagingSource<Int, DiscoverMovies>() {

    override fun getRefreshKey(state: PagingState<Int, DiscoverMovies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovies> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiCalls.discoverMovies(currentPage)
            LoadResult.Page(
                data = response.results,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.results.isEmpty()) null else currentPage + 1
            )
        } catch (throwable: Throwable) {
            return LoadResult.Error(throwable)
        }
    }
}