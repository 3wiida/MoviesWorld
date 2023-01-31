package com.ewida.rickmorti.custom_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.TAG
import com.google.android.material.textfield.TextInputLayout

@SuppressLint("CustomViewStyleable", "ResourceAsColor")
class MovieWorldEditText(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    val inputEt: EditText
    private val boxImage: ImageView
    private val boxLabel: TextView
    private var drawable: Drawable? = null
    private val errorTv: TextView
    private val inputLayout:TextInputLayout


    init {
        inflate(context, R.layout.custom_edit_text, this)

        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.movieWorldEditText)
        val txt = attrs.getString(R.styleable.movieWorldEditText_boxLabel)
        val image = attrs.getDrawable(R.styleable.movieWorldEditText_boxImage)
        val type = attrs.getInt(R.styleable.movieWorldEditText_setInputType, 1)
        val boxDrawable = attrs.getDrawable(R.styleable.movieWorldEditText_boxDrawable)
        val boxError = attrs.getString(R.styleable.movieWorldEditText_boxError)

        boxLabel = findViewById(R.id.label)
        boxImage = findViewById(R.id.boxImage)
        inputEt = findViewById(R.id.boxEt)
        errorTv = findViewById(R.id.boxError)
        inputLayout =findViewById(R.id.inputLayout)

        inputEt.inputType = type
        boxLabel.text = txt
        boxImage.setImageDrawable(image)
        drawable = boxDrawable
        errorTv.text = boxError.toString()

        if (!boxError.isNullOrEmpty())
            errorTv.visibility = View.VISIBLE
        else
            errorTv.visibility = View.GONE

        if(type==129){
            inputLayout.endIconMode=TextInputLayout.END_ICON_PASSWORD_TOGGLE
            inputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.white)))
            inputLayout.setEndIconDrawable(R.drawable.password_toggle_selector)
        }

        attrs.recycle()
    }

    fun changeDrawableVisibility(state: Boolean) {
        if (state) {
            inputEt.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        } else {
            inputEt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    fun getText(): String = inputEt.text.toString()

}