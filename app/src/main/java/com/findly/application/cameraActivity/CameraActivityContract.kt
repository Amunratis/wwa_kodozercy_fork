package com.findly.application.cameraActivity

import com.findly.application.base.MvpPresenter
import com.findly.application.base.MvpView

/**
 * Created by patrykgalazka on 20.04.2018.
 */
interface CameraActivityContract {
    interface View : MvpView {
        fun takePicture()
    }

    interface Presenter : MvpPresenter<View>
}