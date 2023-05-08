package com.ewida.rickmorti.ui.home.fragments.lists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.ListItemLayoutBinding
import com.ewida.rickmorti.model.created_lists_response.CreatedLists

class ListsAdapter :
    ListAdapter<CreatedLists, ListsAdapter.ItemViewHolder>(Comparator) {

    val onItemClicked: ((list: CreatedLists) -> Unit)? = null
    var onItemDeleted: ((list: CreatedLists) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    inner class ItemViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CreatedLists) {
            binding.list=item
            binding.root.setOnClickListener { onItemClicked?.invoke(item) }
            binding.ivDelete.setOnClickListener { onItemDeleted?.invoke(item) }
        }
    }

    private object Comparator : DiffUtil.ItemCallback<CreatedLists>() {
        override fun areItemsTheSame(oldItem: CreatedLists, newItem: CreatedLists) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CreatedLists, newItem: CreatedLists) =
            oldItem == newItem
    }


}