package com.ewida.rickmorti.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common
import com.ewida.rickmorti.common.Common.IMAGE_URL
import com.ewida.rickmorti.common.Keys
import com.ewida.rickmorti.common.Keys.YEAR
import com.ewida.rickmorti.custom_view.LoadingImage
import com.ewida.rickmorti.custom_view.MovieWorldEditText
import com.ewida.rickmorti.utils.date_time_utils.DateTimeUtils

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

@BindingAdapter("loadingImageUrl")
fun LoadingImage.setImageUrl(url:String?){
    this.setImage(IMAGE_URL+url)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("releasedYear")
fun TextView.setYear(date:String?){
    date?.let {
        this.text="(${DateTimeUtils.getDateDetails(date = it, pattern = Common.DATE_PATTERN)[YEAR]})"
    }
}