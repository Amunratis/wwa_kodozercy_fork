package com.findly.application.base

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.findly.R

/**
 * Created by Wojdor on 20.04.2018.
 */

abstract class BaseActivity : AppCompatActivity() {

    fun replaceFragment(fragment: BaseFragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.activityMainContainerFl, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commitAllowingStateLoss()
    }
}
