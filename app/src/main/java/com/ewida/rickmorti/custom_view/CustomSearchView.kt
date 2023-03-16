package com.ewida.rickmorti.custom_view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.ewida.rickmorti.R
import kotlinx.coroutines.*

class CustomSearchView(context: Context, attributeSet: AttributeSet) :
    CardView(context, attributeSet) {

    private val viewParent: CardView
    private val box: ConstraintLayout
    private val searchIcon: ImageView
    private val searchEditText: EditText
    private val clearIcon: ImageView
    private val boxPadding: Int
    private val boxTextSize: Int
    private val boxFontFamily: Int
    private val boxTextColor: Int
    private val boxSearchIcon: Int
    private val boxClearIcon: Int
    private var boxHint: String = "Search"
    private val boxBackground: Int
    private val boxHeight: Int
    private val boxPaddingRight:Int
    private val boxPaddingLeft:Int
    private val boxPaddingTop:Int
    private val boxPaddingBottom:Int
    private lateinit var doActionJob: Job
    private val actionHandler:Handler by lazy{
        Handler(Looper.getMainLooper())
    }

    init {
        inflate(context, R.layout.search_view_layout, this)
        viewParent = findViewById(R.id.parent)
        box = findViewById(R.id.box)
        searchIcon = findViewById(R.id.searchIcon)
        clearIcon = findViewById(R.id.deleteIcon)
        searchEditText = findViewById(R.id.searchEt)

        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSearchView)
        boxPadding = attrs.getInteger(R.styleable.CustomSearchView_boxPadding, 5)
        boxBackground = attrs.getColor(R.styleable.CustomSearchView_boxBackground, -1)
        boxHeight = attrs.getInteger(R.styleable.CustomSearchView_boxHeight, 20)
        boxTextSize = attrs.getInteger(R.styleable.CustomSearchView_boxTextSize, 14)
        boxFontFamily = attrs.getResourceId(R.styleable.CustomSearchView_boxFontFamily, -1)
        boxTextColor = attrs.getColor(R.styleable.CustomSearchView_boxTextColor, -1)
        boxSearchIcon = attrs.getResourceId(R.styleable.CustomSearchView_boxSearchIcon, -1)
        boxClearIcon = attrs.getResourceId(R.styleable.CustomSearchView_boxClearIcon, -1)

        boxPaddingRight=attrs.getInteger(R.styleable.CustomSearchView_boxPaddingRight,0)
        boxPaddingLeft=attrs.getInteger(R.styleable.CustomSearchView_boxPaddingLeft,0)
        boxPaddingTop=attrs.getInteger(R.styleable.CustomSearchView_boxPaddingTop,0)
        boxPaddingBottom=attrs.getInteger(R.styleable.CustomSearchView_boxPaddingBottom,0)

        attrs.getString(R.styleable.CustomSearchView_boxHint)?.let {
            boxHint = it
        }
        attrs.recycle()
        setUpView()
        initClicks()
    }


    private fun setUpView() {
        box.setPadding(boxPaddingLeft, boxPaddingTop, boxPaddingRight, boxPaddingBottom)
        searchEditText.textSize = boxTextSize.toFloat()
        searchEditText.height = boxHeight
        searchEditText.setHintTextColor(ColorStateList.valueOf(context.getColor(R.color.textSecondaryColor)))
        searchEditText.hint=boxHint
        if (boxTextColor != -1) searchEditText.setTextColor(boxTextColor)
        if (boxSearchIcon != -1) searchIcon.setImageResource(boxSearchIcon)
        if (boxClearIcon != -1) clearIcon.setImageResource(boxClearIcon)
        if (boxBackground != -1) viewParent.setCardBackgroundColor(boxBackground)
        watchText(duration = 1)
    }

    fun getSearchQuery() = searchEditText.text.toString()

    fun setSearchQuery(query: String) {
        searchEditText.setText(query)
    }

     fun watchText(action: ((String) -> Unit) ?= null, duration: Long, loading:(()->Unit)?=null,emptyAction:(()->Unit)?=null) {
        searchEditText.doAfterTextChanged { text->
            actionHandler.removeCallbacksAndMessages(null)
            if (getSearchQuery().isNotEmpty())
                clearIcon.visibility = View.VISIBLE
            else
                clearIcon.visibility = View.GONE

            loading?.invoke()

            if(text.toString().isEmpty()){
                actionHandler.postDelayed({
                    emptyAction?.invoke()
                },1000)
            }else{
                actionHandler.postDelayed({
                    action?.invoke(text.toString())
                },duration)
            }

        }
    }

    private fun initClicks(){
        clearIcon.setOnClickListener {
            searchEditText.setText("")
        }
    }


}