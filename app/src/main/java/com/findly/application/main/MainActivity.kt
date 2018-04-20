package com.findly.application.main

import android.os.Bundle
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.application.main.recognise.RecogniseFragment

class MainActivity : BaseActivity(), MainActivityContract.View {
    override fun showData() {

    }

    var presenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        replaceFragment(RecogniseFragment())
    }
}
