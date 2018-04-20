package com.findly.application.main

import com.findly.application.base.BasePresenter
import com.findly.application.base.BaseView

/**
 * Created by Wojdor on 20.04.2018.
 */

interface MainContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter<View>
}