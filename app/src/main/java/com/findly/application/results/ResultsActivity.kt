package com.findly.application.results

import android.os.Bundle
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.application.main.recognised.ProfileFragment
import com.findly.application.main.recognised.RecognisedFragment
import com.findly.application.main.recognised.SettingsFragment
import com.findly.application.main.recognised.UnrecognisedFragment
import kotlinx.android.synthetic.main.activity_main.*

class ResultsActivity : BaseActivity(), ResultsContract.View {

    var presenter: ResultsContract.Presenter = ResultsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        presenter.attachView(this)
    }
}
