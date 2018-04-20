package com.findly.application.main

import android.os.Bundle
import com.findly.R
import com.findly.application.base.BaseActivity

class MainActivity : BaseActivity(), MainActivityContract.View {
    override fun showData() {

    }

    var presenter: MainActivityContract.MvpPresenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        presenter.downloadData()
    }
}
