package com.ewida.rickmorti.custom_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ewida.rickmorti.R

class LoadingImage(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet), RequestListener<Drawable> {
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
        startFailureAnimation(R.raw.image_loading_failure)
        return true
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        imageLoader.setAnimation(R.raw.image_loader)
        imageLoader.playAnimation()
        imageView.animation=AnimationUtils.loadAnimation(this.context,android.R.anim.fade_in)
        imageView.setImageDrawable(resource)
        imageLoader.visibility = View.GONE
        return true
    }

    private fun startFailureAnimation(lottieAnimation:Int){
        imageLoader.setAnimation(lottieAnimation)
        imageLoader.playAnimation()
    }


}