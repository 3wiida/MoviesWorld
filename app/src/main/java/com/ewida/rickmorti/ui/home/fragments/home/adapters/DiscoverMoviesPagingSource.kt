package com.ewida.rickmorti.ui.home.fragments.home.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.model.dicover_movie_response.DiscoverMovies
import javax.inject.Inject

class DiscoverMoviesPagingSource @Inject constructor(private val apiCalls: ApiCalls) :
    PagingSource<Int, DiscoverMovies>() {

    override fun getRefreshKey(state: PagingState<Int, DiscoverMovies>): Int? {
        TODO()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovies> {
        val currentPage = params.key ?: 1
        val response = apiCalls.discoverMovies(currentPage)
        return try {
            LoadResult.Page(
                data = response.results,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.results.isEmpty()) null else currentPage + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}