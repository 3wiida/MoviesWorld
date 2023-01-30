package com.ewida.rickmorti.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ewida.rickmorti.R

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url:String?){
    if(url.isNullOrBlank()){
        Glide.with(context).load(url).placeholder(R.drawable.placeholder).into(this)
    }
}