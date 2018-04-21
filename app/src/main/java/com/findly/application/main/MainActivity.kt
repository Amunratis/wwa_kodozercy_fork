package com.findly.application.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.application.cameraActivity.CameraActivity
import com.findly.application.main.recognised.ProfileFragment
import com.findly.application.main.recognised.RecognisedFragment
import com.findly.application.main.recognised.SettingsFragment
import com.findly.application.main.recognised.UnrecognisedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainActivityContract.View {

    var presenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        askPermissions()
        replaceFragment(UnrecognisedFragment())
        initListeners()
    }

    private fun startCameraActivity() {
        startActivity(Intent(this, CameraActivity::class.java))
    }

    private fun askPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE).toTypedArray()
                        , 1)
            }
        }
    }

    private fun initListeners() {
        activityMainUnrecognisedIv.setOnClickListener { replaceFragment(UnrecognisedFragment()) }
        activityMainRecognisedIv.setOnClickListener { replaceFragment(RecognisedFragment()) }
        activityMainCameraIv.setOnClickListener { startCameraActivity() }
        activityMainSettingsIv.setOnClickListener { replaceFragment(SettingsFragment()) }
        activityMainProfileIv.setOnClickListener { replaceFragment(ProfileFragment()) }
    }
}
