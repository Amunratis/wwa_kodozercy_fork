package com.findly.application.main.unrecognised

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.findly.R
import com.findly.data.firebase.model.Post

/**
 * Created by Wojdor on 21.04.2018.
 */
class UnrecognisedAdapter : RecyclerView.Adapter<UnrecognisedViewHolder>() {
    private val NO_CLICK_EVENT: (Post) -> Unit = {}
    private var clickEvent: (Post) -> Unit = NO_CLICK_EVENT
    private val posts: MutableList<Post> = mutableListOf()

    fun updatePosts(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnrecognisedViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_unrecognised, parent, false)
        return UnrecognisedViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnrecognisedViewHolder, position: Int) {
        holder.onBind(posts[position])
        holder.tryAttachClickListener()
    }

    fun onItemClick(click: (item: Post) -> Unit) {
        clickEvent = click
    }

    override fun getItemCount(): Int = posts.count()

    private fun isClickListenerAttached(): Boolean = clickEvent != NO_CLICK_EVENT

    private fun UnrecognisedViewHolder.tryAttachClickListener() {
        if (isClickListenerAttached()) {
            this.onClick { clickPosition ->
                clickEvent(posts[clickPosition])
            }
        }
    }
}