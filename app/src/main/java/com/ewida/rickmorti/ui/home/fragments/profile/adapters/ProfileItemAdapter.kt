package com.ewida.rickmorti.ui.home.fragments.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.databinding.ProfileItemLayoutBinding
import com.ewida.rickmorti.model.profile_item.ProfileItemModel

class ProfileItemAdapter: ListAdapter<ProfileItemModel,ProfileItemAdapter.ItemViewHolder>(Comparator) {
    var onItemClick:((ProfileItemModel)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding=ProfileItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class ItemViewHolder(private val binding:ProfileItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:ProfileItemModel){
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
            binding.image.setImageResource(item.image)
            binding.title.text=item.title
        }
    }

    private object Comparator:DiffUtil.ItemCallback<ProfileItemModel>(){
        override fun areItemsTheSame(
            oldItem: ProfileItemModel,
            newItem: ProfileItemModel
        )=newItem==oldItem

        override fun areContentsTheSame(
            oldItem: ProfileItemModel,
            newItem: ProfileItemModel
        )=newItem.id==oldItem.id

    }
}