package com.ewida.rickmorti.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.ewida.rickmorti.R


class EmptyStateView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {
    private val animationView: LottieAnimationView
    private val emptyTextView: TextView
    private val emptySecondTextView: TextView
    private val emptyBtn: MainBtn
    private val animation: Int
    private val emptyText: String?
    private val secondText: String?
    private val btnText: String?
    private var isSecondTextEnabled: Boolean
    private var isButtonEnabled: Boolean
    var onBtnClick: (() -> Unit)? = null

    init {
        inflate(context, R.layout.empty_state_layout, this)
        animationView = findViewById(R.id.emptyViewAnimation)
        emptyTextView = findViewById(R.id.emptyViewText)
        emptySecondTextView = findViewById(R.id.secondText)
        emptyBtn = findViewById(R.id.emptyBtn)
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.EmptyStateView)
        animation = attrs.getResourceId(R.styleable.EmptyStateView_animation, -1)
        emptyText = attrs.getString(R.styleable.EmptyStateView_emptyViewText)
        isButtonEnabled = attrs.getBoolean(R.styleable.EmptyStateView_isBtnEnabled, false)
        isSecondTextEnabled =
            attrs.getBoolean(R.styleable.EmptyStateView_isSecondTextEnabled, false)
        secondText = attrs.getString(R.styleable.EmptyStateView_secondText)
        btnText = attrs.getString(R.styleable.EmptyStateView_btnTxt)
        attrs.recycle()
        setUpView()
    }

    private fun setUpView() {
        emptyText?.let { emptyTextView.text = it }
        if (animation != -1) animationView.setAnimation(animation)
        if (isSecondTextEnabled) {
            emptySecondTextView.isVisible = true
            emptySecondTextView.text = secondText
        } else {
            emptySecondTextView.isVisible = false
        }

        emptyBtn.isVisible = isButtonEnabled
        emptyBtn.setOnClickListener {
            onBtnClick?.invoke()
        }
        emptyBtn.setText(btnText)
    }
}