package com.ewida.rickmorti.ui.home.fragments.profile.adapters;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.ContactRecyclerItemLayoutBinding
import com.ewida.rickmorti.model.contact_model.ContactMethod


class ContactMethodsAdapter :
    ListAdapter<ContactMethod, ContactMethodsAdapter.ItemViewHolder>(Comparator) {

    var onItemClick: ((ContactMethod) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ContactRecyclerItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class ItemViewHolder(private val binding: ContactRecyclerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactMethod) {
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
            binding.contactImage.setImageResource(item.image)
        }
    }

    private object Comparator : DiffUtil.ItemCallback<ContactMethod>() {
        override fun areItemsTheSame(
            oldItem: ContactMethod,
            newItem: ContactMethod
        ) = newItem == oldItem

        override fun areContentsTheSame(
            oldItem: ContactMethod,
            newItem: ContactMethod
        ) = newItem.id == oldItem.id

    }
}