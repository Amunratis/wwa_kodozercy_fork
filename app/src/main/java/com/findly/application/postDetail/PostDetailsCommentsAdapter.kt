package com.findly.application.postDetail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.findly.R
import com.findly.data.firebase.model.Comment

/**
 * Created by patrykgalazka on 21.04.2018.
 */
class PostDetailsCommentsAdapter : RecyclerView.Adapter<PostDetailsCommentsViewHolder>() {
    private val NO_CLICK_EVENT: (Comment) -> Unit = {}
    private var clickEvent: (Comment) -> Unit = NO_CLICK_EVENT
    private val comments: MutableList<Comment> = mutableListOf()

    fun updateComments(comments: List<Comment>) {
        this.comments.clear()
        this.comments.addAll(comments)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailsCommentsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_coment, parent, false)
        return PostDetailsCommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostDetailsCommentsViewHolder, position: Int) {
        holder.onBind(comments[position])
        holder.tryAttachClickListener()
    }

    fun onItemClick(click: (item: Comment) -> Unit) {
        clickEvent = click
    }

    override fun getItemCount(): Int = comments.count()

    private fun isClickListenerAttached(): Boolean = clickEvent != NO_CLICK_EVENT

    private fun PostDetailsCommentsViewHolder.tryAttachClickListener() {
        if (isClickListenerAttached()) {
            this.onClick { clickPosition ->
                clickEvent(comments[clickPosition])
            }
        }
    }
}