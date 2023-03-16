package com.ewida.rickmorti.ui.home.fragments.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.ui.home.fragments.search.adapters.SearchPagingSource
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiCalls: ApiCalls) {
    fun searchMovie(callId:Int,query:String?=null)=Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 1,enablePlaceholders = false),
        pagingSourceFactory = {SearchPagingSource(apiCalls = apiCalls, callId = callId  ,query = query)}
    )
}