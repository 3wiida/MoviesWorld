package com.ewida.rickmorti.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ewida.rickmorti.R
import com.ewida.rickmorti.custom_view.MovieWorldEditText

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context).load(url).placeholder(R.drawable.placeholder).into(this)
    }
}

@BindingAdapter("boxErrorText")
fun setBoxErrorText(editText:MovieWorldEditText,errorMsg: String?) {
    if(!errorMsg.isNullOrEmpty()){
        editText.getErrorTextView().visibility=View.VISIBLE
        editText.getErrorTextView().text=errorMsg
    }else{
        editText.getErrorTextView().visibility=View.GONE
        editText.getErrorTextView().text=""
    }

}