package com.findly.application.results

import com.findly.application.base.MvpPresenter
import com.findly.application.base.MvpView
import com.findly.data.service.response.Offers

/**
 * Created by patrykgalazka on 20.04.2018.
 */
interface ResultsContract {

    interface View : MvpView {
        fun showOffers(offers: List<Offers>)
        fun switchLoading(isLoading: Boolean)
    }

    interface Presenter : MvpPresenter<View> {
        fun downloadOffers(phrase: String)
    }
}