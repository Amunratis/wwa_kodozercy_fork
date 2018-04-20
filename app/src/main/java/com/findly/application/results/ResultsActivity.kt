package com.findly.application.results

import android.os.Bundle
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.data.service.AllegroService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResultsActivity : BaseActivity(), ResultsContract.View {

    var presenter: ResultsContract.Presenter = ResultsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        presenter.attachView(this)
        AllegroService.getApiService().getOffers("iphone")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}
