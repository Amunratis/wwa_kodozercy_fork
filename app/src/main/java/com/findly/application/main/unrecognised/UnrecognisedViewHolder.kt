package com.findly.application.main.unrecognised

import android.view.View
import com.findly.application.GlideApp
import com.findly.application.base.CommonViewHolder
import com.findly.data.firebase.model.Post
import kotlinx.android.synthetic.main.item_unrecognised.view.*

/**
 * Created by Wojdor on 21.04.2018.
 */
class UnrecognisedViewHolder(itemView: View) : CommonViewHolder<Post>(itemView) {
    override fun onClick(event: (position: Int) -> Unit) {
        itemView.setOnClickListener { event(adapterPosition) }
    }

    override fun onBind(model: Post) {
        GlideApp.with(itemView).load(model.imageUrl).into(itemView.itemUnrecognisedPhotoIv)
    }
}