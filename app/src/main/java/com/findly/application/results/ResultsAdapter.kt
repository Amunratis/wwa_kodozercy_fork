package com.findly.application.results

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.findly.R
import com.findly.data.service.response.Offers

/**
 * Created by Wojdor on 20.04.2018.
 */
class ResultsAdapter : RecyclerView.Adapter<ResultsViewHolder>() {
    private val NO_CLICK_EVENT: (Offers) -> Unit = {}
    private var clickEvent: (Offers) -> Unit = NO_CLICK_EVENT
    private val updateOffers: MutableList<Offers> = mutableListOf()

    fun updateOffers(updateOffers: List<Offers>) {
        this.updateOffers.clear()
        this.updateOffers.addAll(updateOffers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_offer, parent, false)
        return ResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.onBind(updateOffers[position])
        holder.tryAttachClickListener()
    }

    fun onItemClick(click: (item: Offers) -> Unit) {
        clickEvent = click
    }

    override fun getItemCount(): Int = updateOffers.count()

    private fun isClickListenerAttached(): Boolean = clickEvent != NO_CLICK_EVENT

    private fun ResultsViewHolder.tryAttachClickListener() {
        if (isClickListenerAttached()) {
            this.onClick { clickPosition ->
                clickEvent(updateOffers[clickPosition])
            }
        }
    }
}