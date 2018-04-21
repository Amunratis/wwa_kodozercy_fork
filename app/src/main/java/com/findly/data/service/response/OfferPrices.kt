package com.findly.data.service.response

import com.google.gson.annotations.SerializedName

/**
 * Created by patrykgalazka on 21.04.2018.
 */
class OfferPrices(@SerializedName("bid")
                  var bidPrice: Double,
                  @SerializedName("buyNow")
                  var buyNowPrice: Double)