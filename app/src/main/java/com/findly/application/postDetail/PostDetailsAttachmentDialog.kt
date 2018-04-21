package com.findly.application.postDetail

import android.app.DialogFragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.findly.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.dialog_attachment.*
import java.util.concurrent.TimeUnit

/**
 * Created by patrykgalazka on 21.04.2018.
 */
class PostDetailsAttachmentDialog : DialogFragment() {
    companion object {
        fun newInstance(): PostDetailsAttachmentDialog {
            return PostDetailsAttachmentDialog()
        }
    }

    var textChangeSubject: PublishSubject<String> = PublishSubject.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_attachment, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        subscribeToDataChanged()
    }

    private fun subscribeToDataChanged() {
        textChangeSubject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(50, TimeUnit.MILLISECONDS)
                .subscribe(
                        {
                            downloadOfferForId(getAuctionId(it))
                        }
                )
    }

    private fun downloadOfferForId(auctionId: String) {
    }

    private fun getAuctionId(auctionString: String): String {
        return if (auctionString.length > 15)
            parseAuctionString(auctionString)
        else return auctionString
    }

    private fun parseAuctionString(auctionString: String): String {
        return auctionString.removeRange(0, 0)
    }

    private fun setupListeners() {
        dialogAttachmentAuctionUrl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textChangeSubject.onNext(text.toString())
            }

        })
    }
}