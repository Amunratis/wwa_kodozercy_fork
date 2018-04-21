package com.findly.application.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by Wojdor on 21.04.2018.
 */
class SquareImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)
    }
}