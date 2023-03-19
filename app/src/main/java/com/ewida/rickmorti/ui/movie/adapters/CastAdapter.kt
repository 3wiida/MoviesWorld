package com.ewida.rickmorti.ui.movie.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.databinding.CastSingleItemLayoutBinding
import com.ewida.rickmorti.model.cast_response_model.Cast

class CastAdapter:ListAdapter<Cast,CastAdapter.CastViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding=CastSingleItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class CastViewHolder(val binding:CastSingleItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Cast){
            binding.actor=item
            binding.executePendingBindings()
            binding.actorCharacter.isSelected=true
            Log.d(TAG, "bind: ${item.name}")
        }
    }

    private object Comparator:DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast)=oldItem==newItem

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast)=oldItem.cast_id==newItem.cast_id

    }
}