package com.ewida.rickmorti.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ewida.rickmorti.R

class MainBtn(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {
    private var btnTv: TextView
    private var btnPb: ProgressBar
    private var mainBtn: ConstraintLayout

    init {
        inflate(context, R.layout.main_btn_layout, this)
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.MainBtn)
        btnTv = findViewById(R.id.mainBtnTv)
        btnPb = findViewById(R.id.mainBtnPb)
        mainBtn = findViewById(R.id.mainBtn)
        btnTv.text = attrs.getString(R.styleable.MainBtn_btnText)
        val background=attrs.getResourceId(R.styleable.MainBtn_btnBackground,-1)
        if(background!=-1) mainBtn.setBackgroundResource(background)
        attrs.recycle()
    }

    fun changeLoading(state: Int) {
        when (state) {
            1 -> {
                btnPb.visibility = View.VISIBLE
                btnTv.visibility = View.INVISIBLE
                isBtnEnabled(false)
            }
            0 -> {
                btnTv.visibility = View.VISIBLE
                btnPb.visibility = View.INVISIBLE
                isBtnEnabled(true)
            }
        }
    }

    private fun isBtnEnabled(state: Boolean) {
        if (state) {
            mainBtn.setBackgroundResource(R.drawable.main_btn_background)
            mainBtn.isClickable = false

        } else {
            mainBtn.setBackgroundResource(R.drawable.disabled_main_btn_background)
            mainBtn.isClickable = true

        }
    }

    fun setText(text:String?){
        text?.let {
            btnTv.text=it
        }
    }
}