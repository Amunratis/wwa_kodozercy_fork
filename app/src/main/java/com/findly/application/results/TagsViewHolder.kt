package com.findly.application.results

import android.view.View
import com.findly.application.base.CommonViewHolder
import kotlinx.android.synthetic.main.item_tag.view.*

/**
 * Created by Wojdor on 21.04.2018.
 */
class TagsViewHolder(itemView: View) : CommonViewHolder<String>(itemView) {
    override fun onClick(event: (position: Int) -> Unit) {
        itemView.setOnClickListener { event(adapterPosition) }
    }

    override fun onBind(model: String) {
        itemView.itemTagNameTv.text = model
    }
}