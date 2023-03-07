package com.ewida.rickmorti.ui.home.fragments.search

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.databinding.FragmentSearchBinding
import com.ewida.rickmorti.model.category_model.Category
import com.ewida.rickmorti.ui.home.fragments.search.adapters.CategoryAdapter


class SearchFragment : BaseFragment<FragmentSearchBinding,SearchViewModel>(){
    private val categoryAdapter= CategoryAdapter()
    override val viewModel: SearchViewModel by viewModels()
    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override fun sendCalls() {

    }

    override fun setUpViews() {
        initRecyclers()
        initClicks()
    }

    private fun initRecyclers(){
        categoryAdapter.submitList(prepareCategoryList())
        binding.categoryRecycler.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.categoryRecycler.adapter=categoryAdapter
    }

    private fun prepareCategoryList(): List<Category> {
        return listOf(
            Category("For You", 1,true),
            Category("New Releases", 2),
            Category("Popular Now", 3),
            Category("Top Rated", 4),
        )
    }

    private fun initClicks(){
        categoryAdapter.onItemClick={ touchedItem ->
            val newList = prepareCategoryList()
            newList.forEach { item ->
                item.isChecked = item.categoryId==touchedItem.categoryId
            }
            categoryAdapter.submitList(newList)
        }
    }
}