package com.findly.application.main.recognise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.findly.R
import com.findly.application.base.BaseFragment

/**
 * Created by Wojdor on 20.04.2018.
 */

class RecogniseFragment : BaseFragment(), RecogniseContract.View {

    var presenter: RecogniseContract.Presenter = RecognisePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recognise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }
}
