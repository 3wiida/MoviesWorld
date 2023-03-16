package com.ewida.rickmorti.ui.home.fragments.home.adapters.top_rated

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.model.common_movie_response.CommonMovie
import com.ewida.rickmorti.model.top_rated_response.TopRatedMovie
import javax.inject.Inject

class TopRatedPagingSource @Inject constructor(private val apiCalls: ApiCalls) : PagingSource<Int, CommonMovie>() {
    override fun getRefreshKey(state: PagingState<Int, CommonMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommonMovie> {
        val pageIndex = params.key ?: 1
        return try {
            val response = apiCalls.getTopRated(pageIndex)
            val prevPage=if(pageIndex==1) null else pageIndex+1
            val nextPage=if(response.results.isEmpty()) null else pageIndex+1
            LoadResult.Page(
                data = response.results,
                prevKey = prevPage,
                nextKey = nextPage
            )
        }catch (throwable:Throwable){
            LoadResult.Error(throwable)
        }
    }
}