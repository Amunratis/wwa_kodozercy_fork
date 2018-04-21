package com.findly.data.service.response

import com.google.gson.annotations.SerializedName

/**
 * Created by patrykgalazka on 21.04.2018.
 */
class OfferResponse(
        @SerializedName("name")
        var name: String,
        @SerializedName("gallery")
        var gallery: MutableList<OfferGallery>,
        @SerializedName("buyNow")
        var isBuyNow: Boolean,
        @SerializedName("prices")
        var prices: OfferPrices,
        @SerializedName("id")
        var offerId: String)