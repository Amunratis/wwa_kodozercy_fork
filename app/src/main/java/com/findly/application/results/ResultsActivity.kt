package com.findly.application.results

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.data.service.response.Offers
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : BaseActivity(), ResultsContract.View {

    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }

    var presenter: ResultsContract.Presenter = ResultsPresenter()
    val adapter: ResultsAdapter = ResultsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setupResultsRv()
        presenter.attachView(this)
        presenter.downloadOffers("iphone")
    }

    private fun setupResultsRv() {
        activityResultsOffersRv.setLayoutManager(GridLayoutManager(this, NUMBER_OF_COLUMN))
        activityResultsOffersRv.adapter = adapter
    }

    override fun showOffers(offers: List<Offers>) {
        adapter.updateOffers(offers)
    }
}
