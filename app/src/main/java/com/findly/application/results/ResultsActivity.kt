package com.findly.application.results

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.data.service.response.Offers
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : BaseActivity(), ResultsContract.View {

    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }

    var presenter: ResultsContract.Presenter = ResultsPresenter()
    private val resultsAdapter: ResultsAdapter = ResultsAdapter()
    private val tagsAdapter: TagsAdapter = TagsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setupResultsRv()
        setupTagsRv()
        presenter.attachView(this)
        // TODO: Change phrase
        presenter.downloadOffers("iphone")
    }

    private fun setupResultsRv() {
        activityResultsOffersRv.setLayoutManager(GridLayoutManager(this, NUMBER_OF_COLUMN))
        activityResultsOffersRv.adapter = resultsAdapter
        resultsAdapter.onItemClick { openOffer(it.url) }
    }

    private fun openOffer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun setupTagsRv() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityResultsTagsRv.layoutManager = layoutManager
        activityResultsTagsRv.adapter = tagsAdapter
        LinearSnapHelper().attachToRecyclerView(activityResultsTagsRv)
        // TODO: Change tag list
        tagsAdapter.updateTags(listOf("test0", "test1", "test2", "test3", "test4", "test5", "test6", "test7"))
    }

    override fun showOffers(offers: List<Offers>) {
        resultsAdapter.updateOffers(offers)
    }
}
