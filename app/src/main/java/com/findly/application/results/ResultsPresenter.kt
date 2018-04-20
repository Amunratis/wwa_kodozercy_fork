package com.findly.application.results

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
}