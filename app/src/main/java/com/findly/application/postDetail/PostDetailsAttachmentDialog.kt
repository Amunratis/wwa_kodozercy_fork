package com.findly.application.postDetail

import android.app.DialogFragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.findly.R
import com.findly.application.GlideApp
import com.findly.application.view.hide
import com.findly.application.view.show
import com.findly.data.service.AllegroService
import com.findly.data.service.response.OfferResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.dialog_attachment.*
import kotlinx.android.synthetic.main.item_auction_snippet.*
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

    lateinit var offerId: String
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
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(
                        {
                            downloadOfferForId(getAuctionId(it))
                        }
                )
    }

    private fun downloadOfferForId(auctionId: String) {
        AllegroService.getMobileApiService()
                .getOffer(auctionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            offerId = auctionId
                            setupAuctionLayout(it)
                        },
                        {
                            setupErrorLayout()
                        }
                )
    }

    private fun setupErrorLayout() {
        itemAuctionSnippetContainer.hide()
        itemAuctionSnippetWrongAuctionTv.show()

    }

    private fun setupAuctionLayout(offerResponse: OfferResponse) {

        itemAuctionSnippetWrongAuctionTv.hide()
        itemAuctionSnippetHeaderTv.text = offerResponse.name
        GlideApp.with(itemAuctionSnippetImageIv).load(offerResponse.gallery.component1().smallImageUrl).into(itemAuctionSnippetImageIv)
        if (offerResponse.isBuyNow)
            itemAuctionSnippetPriceTv.text = """${offerResponse.prices.buyNowPrice}0 zł"""
        else
            itemAuctionSnippetPriceTv.text = """${offerResponse.prices.bidPrice}0 zł"""
        itemAuctionSnippetContainer.show()
    }

    private fun getAuctionId(auctionString: String): String {
        return if (auctionString.length > 15)
            parseAuctionString(auctionString)
        else return auctionString
    }

    private fun parseAuctionString(auctionString: String): String {
        return if (auctionString.contains("item".toRegex()))
            auctionString.substring(auctionString.indexOf("item=", 0, true) + "item=".length)
        else
            auctionString.substring(auctionString.lastIndexOf('i') + 1, auctionString.lastIndexOf('.'))

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
        dialogAttachmentAcceptBtn.setOnClickListener {
            (activity as PostDetailsActivity).offerId = offerId
            dismiss()
        }
        dialogAttachmentDismissBtn.setOnClickListener { dismiss() }
    }
}