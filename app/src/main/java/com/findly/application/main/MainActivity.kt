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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainActivityContract.View {
    override fun showData() {

    }

    var presenter: MainActivityContract.MvpPresenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        presenter.downloadData()
        askPermissions()
        activityMainCameraButton.setOnClickListener { startActivity(Intent(this, CameraActivity::class.java)) }
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
}
