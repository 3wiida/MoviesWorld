package com.ewida.rickmorti.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.TAG

@SuppressLint("CustomViewStyleable")
class MovieWorldEditText(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    val inputEt: EditText
    private val boxImage:ImageView
    private val boxLabel:TextView
    var drawable:Drawable?=null

    init {
        inflate(context, R.layout.custom_edit_text, this)

        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.movieWorldEditText)
        val txt = attrs.getString(R.styleable.movieWorldEditText_boxLabel)
        val image = attrs.getDrawable(R.styleable.movieWorldEditText_boxImage)
        val type = attrs.getInt(R.styleable.movieWorldEditText_setInputType, 1)
        val boxDrawable=attrs.getDrawable(R.styleable.movieWorldEditText_boxDrawable)

        boxLabel= findViewById(R.id.label)
        boxImage= findViewById(R.id.boxImage)
        inputEt = findViewById(R.id.boxEt)

        inputEt.inputType = type
        boxLabel.text = txt
        boxImage.setImageDrawable(image)
        drawable=boxDrawable

        attrs.recycle()
    }

    fun changeDrawableVisibility(state: Boolean) {
        if (state) {
            inputEt.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }else{
            inputEt.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
        }
    }

    fun getText():String = inputEt.text.toString()

}