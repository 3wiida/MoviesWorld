package com.ewida.rickmorti.ui.home.fragments.search

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.paging.LoadState
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.model.category_model.Category
import com.ewida.rickmorti.ui.home.fragments.search.adapters.CategoryAdapter
import com.ewida.rickmorti.ui.home.fragments.search.adapters.SearchResultsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: SearchRepository) : BaseViewModel() {

    /** VARS **/
    val isShowEmptyState = ObservableBoolean(false)
    val isLoading = ObservableBoolean(true)

    /** Functions **/
    fun searchMovie(callId: Int, query: String? = null) = repo.searchMovie(callId, query).flow

    fun getCategoriesList(): List<Category> {
        return listOf(
            Category("Upcoming", 1, true),
            Category("Trending", 2),
            Category("Popular Now", 3),
            Category("Top Rated", 4),
            Category("Arabic Movies", 5),
            Category("Indian Movies", 6),
        )
    }

    fun getSelectedCategory(categoryAdapter: CategoryAdapter): Category? {
        var selectedCategory: Category? = null
        categoryAdapter.currentList.forEach { category ->
            if (category.isChecked) selectedCategory = category
        }
        return selectedCategory
    }

    //TODO Try to make it in empty state layout class
    fun observeEmptyState(adapter: SearchResultsAdapter) {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
                if (adapter.itemCount < 1) {
                    isShowEmptyState.set(true)
                    Log.d("``TAG``", "observeEmptyState: enter empty")
                } else {
                    isShowEmptyState.set(false)
                    Log.d("``TAG``", "observeEmptyState: enter not empty")
                }
            }
        }
    }

    fun observeLoading(adapter: SearchResultsAdapter) {
        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                isLoading.set(true)
            } else {
                isLoading.set(false)
            }
        }
    }
}