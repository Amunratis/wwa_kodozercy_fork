package com.findly.application.main.recognised

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.findly.R
import com.findly.application.base.BaseFragment

/**
 * Created by Wojdor on 20.04.2018.
 */

class ProfileFragment : BaseFragment(), ProfileContract.View {

    var presenter: ProfileContract.Presenter = ProfilePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }
}
