package com.findly.application.cameraActivity

/**
 * Created by patrykgalazka on 20.04.2018.
 */
class CameraActivityPresenter : CameraActivityContract.Presenter {
    var view: CameraActivityContract.View? = null
    override fun attachView(view: CameraActivityContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

}