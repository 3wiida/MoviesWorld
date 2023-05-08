package com.ewida.rickmorti.ui.home.fragments.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.common.Keys
import com.ewida.rickmorti.databinding.FragmentSearchBinding
import com.ewida.rickmorti.ui.home.fragments.search.adapters.CategoryAdapter
import com.ewida.rickmorti.ui.home.fragments.search.adapters.SearchLoadStateAdapter
import com.ewida.rickmorti.ui.home.fragments.search.adapters.SearchResultsAdapter
import com.ewida.rickmorti.ui.movie.MovieDataActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    /** VARS **/
    @Inject
    lateinit var bundle: Bundle
    private val categoryAdapter = CategoryAdapter()
    private val searchAdapter = SearchResultsAdapter()
    override val viewModel: SearchViewModel by viewModels()

    /** Functions **/
    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override fun setUpViews() {
        initRecyclers()
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        if (binding.searchView.getSearchQuery().isBlank()) {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.searchMovie(1).collectLatest { searchAdapter.submitData(it) }
            }
        }
    }

    override fun startObservers() {

        viewModel.observeEmptyState(searchAdapter)
        viewModel.observeLoading(searchAdapter)

        binding.searchView.watchText(action = { query -> sendMovieCall(-1, query) },
            duration = 2000,
            loading = {
                binding.searchLoader.visibility = View.VISIBLE
                binding.resultsRecycler.visibility = View.GONE
                binding.searchEmptyState.visibility = View.GONE
            },
            emptyAction = {
                viewModel.getSelectedCategory(categoryAdapter)
                    ?.let { category -> sendMovieCall(category.categoryId) }
            })
    }

    override fun initClicks() {
        categoryAdapter.onItemClick = { touchedItem ->

            //edit shape
            val newList = viewModel.getCategoriesList()
            newList.forEach { item -> item.isChecked = item.categoryId == touchedItem.categoryId }
            categoryAdapter.submitList(newList)

            //send call
            sendMovieCall(touchedItem.categoryId)
        }

        searchAdapter.onMovieClicked = { movie ->
            bundle.putInt(Keys.MOVIE_ID_KEY, movie.id)
            startActivity(Intent(requireActivity(), MovieDataActivity::class.java))
        }
    }

    private fun initRecyclers() {

        categoryAdapter.submitList(viewModel.getCategoriesList())
        binding.categoryRecycler.adapter = categoryAdapter

        binding.resultsRecycler.adapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = SearchLoadStateAdapter(), footer = SearchLoadStateAdapter()
        )
    }

    private fun sendMovieCall(callId: Int, query: String? = null) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (callId in 1..6) viewModel.searchMovie(callId)
                .collectLatest { searchAdapter.submitData(it) }
            else viewModel.searchMovie(callId, query).collectLatest { searchAdapter.submitData(it) }
        }
    }

}