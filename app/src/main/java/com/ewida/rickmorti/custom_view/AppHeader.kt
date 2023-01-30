package com.ewida.rickmorti.custom_view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ewida.rickmorti.R

class AppHeader(context:Context,attributeSet: AttributeSet):LinearLayout(context,attributeSet) {
    init {
        inflate(context, R.layout.app_header_layout,this)
    }
}