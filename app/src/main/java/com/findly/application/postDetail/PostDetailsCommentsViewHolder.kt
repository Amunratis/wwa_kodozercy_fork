package com.findly.application.postDetail

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.findly.application.GlideApp
import com.findly.application.base.CommonViewHolder
import com.findly.data.firebase.model.Comment
import com.findly.data.service.AllegroService
import com.findly.data.service.response.OfferResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_coment.view.*

/**
 * Created by patrykgalazka on 21.04.2018.
 */
class PostDetailsCommentsViewHolder(itemView: View) : CommonViewHolder<Comment>(itemView) {
    override fun onClick(event: (position: Int) -> Unit) {
        itemView.setOnClickListener { event(adapterPosition) }
    }

    override fun onBind(model: Comment) {
        with(itemView)
        {
            itemCommentUsernameTv.text = "${model.userName}:"
            itemCommentContentTv.text = model.content
            if (model.idAuction.isNotEmpty()) {
                AllegroService.getMobileApiService()
                        .getOffer(model.idAuction)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            setupAuctionSnippetLayout(it)
                        },
                                {

                                })
            }
        }
    }

    private fun setupAuctionSnippetLayout(offerResponse: OfferResponse?) {
        with(itemView) {
            setOnClickListener {
                openOffer("http://allegro.pl/ShowItem2.php?item=${offerResponse?.offerId}")
            }
            itemComentAuctionHeader.text = offerResponse?.name
            GlideApp.with(itemComentAuctionImage).load(offerResponse?.gallery?.component1()?.smallImageUrl).into(itemComentAuctionImage)
            if (offerResponse!!.isBuyNow)
                itemComentAuctionPrice.text = """${offerResponse.prices.buyNowPrice}0 zł"""
            else
                itemComentAuctionPrice.text = """${offerResponse.prices.bidPrice}0 zł"""

        }
    }

    private fun openOffer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(itemView.context, intent, null)
    }
}