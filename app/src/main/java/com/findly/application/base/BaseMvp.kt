package com.findly.application.base

/**
 * Created by patrykgalazka on 20.04.2018.
 */
interface MvpPresenter<in V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}

interface MvpView