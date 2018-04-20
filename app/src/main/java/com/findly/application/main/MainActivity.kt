package com.findly.application.main

import android.os.Bundle
import com.findly.R
import com.findly.application.base.BaseActivity

class MainActivity : BaseActivity(), MainContract.View {

    override val presenter: MainContract.Presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
