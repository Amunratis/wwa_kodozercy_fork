package com.findly.application.main

import android.content.Intent
import android.os.Bundle
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.application.main.recognised.ProfileFragment
import com.findly.application.main.recognised.UnrecognisedFragment
import com.findly.application.results.ResultsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainActivityContract.View {

    var presenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        replaceFragment(UnrecognisedFragment())
        activityMainUnrecognisedIv.setImageResource(R.drawable.str_gl_act)
        activityMainProfileIv.setImageResource(R.drawable.profil)
        initListeners()
    }

    private fun initListeners() {
        activityMainUnrecognisedIv.setOnClickListener {
            replaceFragment(UnrecognisedFragment())
            activityMainUnrecognisedIv.setImageResource(R.drawable.str_gl_act)
            activityMainProfileIv.setImageResource(R.drawable.profil)
        }
        activityMainCameraIv.setOnClickListener { startResultsActivity() }
        activityMainProfileIv.setOnClickListener {
            replaceFragment(ProfileFragment())
            activityMainProfileIv.setImageResource(R.drawable.profil_act)
            activityMainUnrecognisedIv.setImageResource(R.drawable.str_gl)
        }
    }

    private fun startResultsActivity() {
        val intent = Intent(this, ResultsActivity::class.java)
        startActivity(intent)
    }
}
