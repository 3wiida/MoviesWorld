package com.ewida.rickmorti.ui.home.fragments.search.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.CategoryItemLayoutBinding
import com.ewida.rickmorti.model.category_model.Category

class CategoryAdapter:ListAdapter<Category,CategoryAdapter.ItemViewHolder>(Comparator) {

    var onItemClick: ((Category) -> Unit)? = null

    inner class ItemViewHolder(val binding:CategoryItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Category, context: Context){
            binding.categoryName.text=item.categoryName
            if(item.isChecked){
                binding.categoryName.setBackgroundResource(R.drawable.checked_category_item_background)
                binding.categoryName.typeface= Typeface.createFromAsset(context.assets,"poppins_semi_bold.ttf")
            }else{
                binding.categoryName.setBackgroundColor(Color.TRANSPARENT)
                binding.categoryName.typeface= Typeface.createFromAsset(context.assets,"poppins_regular.ttf")
            }
        }
    }

    private object Comparator:DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category)=oldItem.categoryId==newItem.categoryId
        override fun areContentsTheSame(oldItem: Category, newItem: Category)=oldItem==newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding=CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item=getItem(position)
        item?.let{
            holder.bind(it,holder.itemView.context)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }


}