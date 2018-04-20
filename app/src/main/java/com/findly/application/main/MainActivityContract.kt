package com.findly.application.main

import com.findly.application.base.MvpPresenter
import com.findly.application.base.MvpView

/**
 * Created by patrykgalazka on 20.04.2018.
 */
interface MainActivityContract {

    interface View : MvpView {
    }

    interface Presenter : MvpPresenter<View> {

    }
}