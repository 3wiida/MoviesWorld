package com.ewida.rickmorti.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.ewida.rickmorti.R
import com.ewida.rickmorti.custom_view.MovieWorldEditText

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    val requestData=Glide.with(this).asDrawable().sizeMultiplier(0.05f)
    if (!url.isNullOrBlank()) {
        Glide.with(this).load(url).thumbnail(requestData).transition(withCrossFade()).into(this)
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