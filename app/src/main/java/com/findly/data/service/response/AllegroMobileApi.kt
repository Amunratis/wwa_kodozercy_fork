package com.findly.data.service.response

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by patrykgalazka on 21.04.2018.
 */
interface AllegroMobileApi {
    companion object {
        const val BASE_URL = "https://api.natelefon.pl/"
    }

    @GET("v1/allegro/offers/{offerId}")
    fun getOffer(@Path("offerId") offerId: String): Single<OfferResponse>
}