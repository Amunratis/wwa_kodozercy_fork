package com.findly.application.splash

import android.content.Intent
import android.os.Bundle
import com.findly.application.base.BaseActivity
import com.findly.application.main.MainActivity

/**
 * Created by Wojdor on 21.04.2018.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}