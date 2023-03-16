package com.ewida.rickmorti.ui.home.fragments.search

import androidx.lifecycle.ViewModel
import com.ewida.rickmorti.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo:SearchRepository):BaseViewModel() {
    fun searchMovie(callId:Int,query:String?=null) = repo.searchMovie(callId,query).flow
}