package com.findly.application.main

/**
 * Created by patrykgalazka on 20.04.2018.
 */
class MainActivityPresenter : MainActivityContract.MvpPresenter {
    override fun downloadData() {
        view?.showData()
    }

    var view: MainActivityContract.View? = null
    override fun attachView(view: MainActivityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}