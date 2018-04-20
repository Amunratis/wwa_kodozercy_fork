package com.findly.application.results

import com.findly.data.service.AllegroService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by patrykgalazka on 20.04.2018.
 */
class ResultsPresenter : ResultsContract.Presenter {
    var view: ResultsContract.View? = null

    override fun attachView(view: ResultsContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun downloadOffers(phrase: String) {
        AllegroService.getApiService().getOffers(phrase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.showOffers(it.offers) }
    }
}