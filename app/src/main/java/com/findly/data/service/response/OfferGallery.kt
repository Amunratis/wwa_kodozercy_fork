package com.findly.data.service.response

import com.google.gson.annotations.SerializedName

/**
 * Created by patrykgalazka on 21.04.2018.
 */
data class OfferGallery(
        @SerializedName("small")
        var smallImageUrl: String)