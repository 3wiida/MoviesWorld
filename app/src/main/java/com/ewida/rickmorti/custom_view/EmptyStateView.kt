package com.ewida.rickmorti.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.ewida.rickmorti.R


class EmptyStateView(context: Context, attributeSet: AttributeSet):LinearLayout(context,attributeSet) {
    private val animationView:LottieAnimationView
    private val emptyTextView:TextView
    private val animation:Int
    private val emptyText:String?
    init {
        inflate(context, R.layout.empty_state_layout,this)
        animationView=findViewById(R.id.emptyViewAnimation)
        emptyTextView=findViewById(R.id.emptyViewText)
        val attrs=context.obtainStyledAttributes(attributeSet,R.styleable.EmptyStateView)
        animation=attrs.getResourceId(R.styleable.EmptyStateView_animation,-1)
        emptyText=attrs.getString(R.styleable.EmptyStateView_emptyViewText)
        attrs.recycle()
        setUpView()
    }

    private fun setUpView(){
        emptyText?.let {  emptyTextView.text=it }
        if(animation!=-1) animationView.setAnimation(animation)
    }
}