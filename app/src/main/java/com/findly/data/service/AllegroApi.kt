package com.findly.data.service

import com.findly.data.service.response.OffersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Wojdor on 20.04.2018.
 */

interface AllegroApi {
    companion object {
        const val BASE_URL = "https://allegroapi.io"
    }

    @GET("/offers")
    fun getOffers(@Query("phrase") phrase: String): Observable<OffersResponse>
}