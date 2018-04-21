package com.findly.application.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.findly.R

/**
 * Created by patrykgalazka on 21.04.2018.
 */
class FullScreenLoadingDialog(context: Context) : Dialog(context) {
    companion object {
        fun newProgressDialogInstance(context: Context): FullScreenLoadingDialog = FullScreenLoadingDialog(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_loading)
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
    }
}