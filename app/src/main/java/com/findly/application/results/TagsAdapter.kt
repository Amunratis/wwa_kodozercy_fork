package com.findly.application.results

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.findly.R

/**
 * Created by Wojdor on 21.04.2018.
 */

class TagsAdapter : RecyclerView.Adapter<TagsViewHolder>() {
    private val NO_CLICK_EVENT: (String) -> Unit = {}
    private var clickEvent: (String) -> Unit = NO_CLICK_EVENT
    private val tags: MutableList<String> = mutableListOf()

    fun updateTags(tags: List<String>) {
        this.tags.clear()
        this.tags.addAll(tags)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tag, parent, false)
        return TagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.onBind(tags[position])
        holder.tryAttachClickListener()
    }

    fun onItemClick(click: (item: String) -> Unit) {
        clickEvent = click
    }

    override fun getItemCount(): Int = tags.count()

    private fun isClickListenerAttached(): Boolean = clickEvent != NO_CLICK_EVENT

    private fun TagsViewHolder.tryAttachClickListener() {
        if (isClickListenerAttached()) {
            this.onClick { clickPosition ->
                clickEvent(tags[clickPosition])
            }
        }
    }
}