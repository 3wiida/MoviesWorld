package com.ewida.rickmorti.utils.recycler_decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecorator(private val horizontalSpacing:Int):RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if(parent.getChildAdapterPosition(view)%2!=0) outRect.right=horizontalSpacing
    }
}