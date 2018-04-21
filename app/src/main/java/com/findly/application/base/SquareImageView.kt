package com.findly.application.base

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by patrykgalazka on 21.04.2018.
 */

class SquareImageView(context: Context, attributeSet: AttributeSet) : ImageView(context, attributeSet) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth())
    }
}