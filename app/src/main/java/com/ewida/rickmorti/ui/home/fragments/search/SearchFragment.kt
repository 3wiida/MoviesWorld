package com.ewida.rickmorti.ui.home.fragments.search

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.databinding.FragmentSearchBinding
import com.ewida.rickmorti.model.category_model.Category
import com.ewida.rickmorti.ui.home.fragments.home.adapters.trending.TrendingMovieLoadingStateAdapter
import com.ewida.rickmorti.ui.home.fragments.search.adapters.CategoryAdapter
import com.ewida.rickmorti.ui.home.fragments.search.adapters.SearchLoadStateAdapter
import com.ewida.rickmorti.ui.home.fragments.search.adapters.SearchResultsAdapter
import com.ewida.rickmorti.utils.recycler_decoration.SpacingItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private val categoryAdapter = CategoryAdapter()
    private val searchAdapter = SearchResultsAdapter()
    private lateinit var searchAdapterDataObserver:RecyclerView.AdapterDataObserver
    override val viewModel: SearchViewModel by viewModels()
    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override fun sendCalls() {}

    override fun setUpViews() {
        initRecyclers()
        initClicks()
        resultRecyclerObserver()

        binding.searchView.watchText(
            action = { query -> sendMovieCall(-1,query) },
            duration = 2000,
            loading = {
                binding.searchLoader.visibility = View.VISIBLE
                binding.resultsRecycler.visibility = View.GONE
                binding.searchEmptyState.visibility=View.GONE
            },
            emptyAction = {
                getSelectedCategory()?.let {category-> sendMovieCall(category.categoryId)}
            })

        searchAdapter.addLoadStateListener { loadState->
            if(loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached){
                if(searchAdapter.itemCount<1){
                    binding.searchEmptyState.visibility=View.VISIBLE
                    binding.resultsRecycler.visibility=View.GONE
                }else{
                    binding.searchEmptyState.visibility=View.GONE
                    binding.resultsRecycler.visibility=View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(binding.searchView.getSearchQuery().isBlank()){
            lifecycleScope.launch(Dispatchers.IO){
                viewModel.searchMovie(1).collectLatest { searchAdapter.submitData(it) }
            }
        }
    }

    private fun initClicks() {
        categoryAdapter.onItemClick = { touchedItem ->
            //edit shape
            val newList = prepareCategoryList()
            newList.forEach { item ->
                item.isChecked = item.categoryId == touchedItem.categoryId
            }
            categoryAdapter.submitList(newList)

            //send call
            sendMovieCall(touchedItem.categoryId)

        }
    }

    private fun initRecyclers() {
        //Category Recycler
        categoryAdapter.submitList(prepareCategoryList())
        binding.categoryRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecycler.adapter = categoryAdapter

        //Result Recycler
        binding.resultsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.resultsRecycler.addItemDecoration(SpacingItemDecorator(18))
        binding.resultsRecycler.adapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = TrendingMovieLoadingStateAdapter(),
            footer = TrendingMovieLoadingStateAdapter()
        )
    }

    private fun prepareCategoryList(): List<Category> {
        return listOf(
            Category("Upcoming", 1, true),
            Category("Trending", 2),
            Category("Popular Now", 3),
            Category("Top Rated", 4),
            Category("Arabic Movies", 5),
            Category("Indian Movies", 6),
        )
    }

    private fun sendMovieCall(callId: Int,query: String?=null) {
        lifecycleScope.launch(Dispatchers.IO) {
            if(callId in 1..6)
                viewModel.searchMovie(callId).collectLatest { searchAdapter.submitData(it) }
            else
                viewModel.searchMovie(callId,query).collectLatest { searchAdapter.submitData(it) }
        }
    }

    private fun getSelectedCategory():Category?{
        var selectedCategory:Category?=null
        categoryAdapter.currentList.forEach { category ->
            if(category.isChecked) selectedCategory=category
        }
        return selectedCategory
    }


    /** Observers **/
    private fun resultRecyclerObserver() {
        searchAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.searchLoader.visibility = View.GONE
                binding.searchEmptyState.visibility = View.GONE
                binding.resultsRecycler.visibility = View.VISIBLE
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.searchLoader.visibility = View.GONE
            }
        }

        searchAdapter.registerAdapterDataObserver(searchAdapterDataObserver)
    }

    override fun onDestroyView() {
        searchAdapter.unregisterAdapterDataObserver(searchAdapterDataObserver)
        super.onDestroyView()
    }
}