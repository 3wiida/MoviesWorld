package com.ewida.rickmorti.custom_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ewida.rickmorti.R

class LoadingImage(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet), RequestListener<Drawable> {
    private val imageCard: CardView
    private var imageView: ImageView
    private var imageLoader: LottieAnimationView
    private var imageLoadingAnimation: Int
    private var imageFailureAnimation: Int
    private var imageRadius: Int

    init {
        inflate(context, R.layout.custom_loading_image_layout, this)
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingImage)
        imageRadius = attrs.getInteger(R.styleable.LoadingImage_imageRadius, -1)
        imageLoadingAnimation = attrs.getResourceId(R.styleable.LoadingImage_loadingAnimation, -1)
        imageFailureAnimation = attrs.getResourceId(R.styleable.LoadingImage_failureAnimation, -1)
        imageView = findViewById(R.id.imageContent)
        imageLoader = findViewById(R.id.imageLoader)
        imageCard = findViewById(R.id.imageCard)
        attrs.recycle()
        setupView()
    }

    private fun setupView() {
        if (imageRadius != -1) imageCard.radius = imageRadius.toFloat()
        if (imageLoadingAnimation != -1) imageLoader.setAnimation(imageLoadingAnimation)
    }

    fun setImage(image: Any) {
        imageLoader.visibility = visibility
        Glide.with(this).load(image).addListener(this).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .timeout(5000000).into(imageView)
    }


    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        if (imageFailureAnimation != -1) {
            imageLoader.setAnimation(imageFailureAnimation)
            imageLoader.playAnimation()
        }
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
        imageView.animation = AnimationUtils.loadAnimation(this.context, android.R.anim.fade_in)
        imageView.setImageDrawable(resource)
        imageLoader.visibility = View.GONE
        return true
    }
}