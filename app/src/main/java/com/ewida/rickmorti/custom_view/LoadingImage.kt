package com.ewida.rickmorti.custom_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.TAG

class LoadingImage(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet), RequestListener<Drawable> {
    private val requestData = Glide.with(this).asDrawable().sizeMultiplier(0.05f)
    private var imageView: ImageView
    private var imageLoader: LottieAnimationView

    init {
        inflate(context, R.layout.custom_loading_image_layout, this)
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingImage)
        imageView = findViewById(R.id.imageContent)
        imageLoader = findViewById(R.id.imageLoader)
        attrs.recycle()
    }

    fun setImage(image: Any) {
        imageLoader.visibility=visibility
        Glide.with(this).load(image).addListener(this).into(imageView)
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        Log.d(TAG, "onLoadFailed: ${e?.localizedMessage}")
        return true
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        Handler(Looper.getMainLooper()).postDelayed({
            Glide.with(this).load(resource).thumbnail(requestData)
                .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
        },1)
        imageLoader.visibility = View.GONE
        return true
    }


}