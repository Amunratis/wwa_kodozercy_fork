package com.findly.application.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Wojdor on 20.04.2018.
 */
abstract class CommonViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView), OnClickViewHolder, OnBindViewHolder<T>

interface OnClickViewHolder {
    fun onClick(event: (position: Int) -> Unit)
}

interface OnBindViewHolder<in T> {
    fun onBind(model: T)
}