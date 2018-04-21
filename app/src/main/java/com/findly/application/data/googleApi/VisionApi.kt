package com.findly.application.data.googleApi

import com.findly.application.data.googleApi.model.VisionRequestBody
import com.findly.application.data.googleApi.model.VisionResponses
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by patrykgalazka on 20.04.2018.
 */
interface VisionApi {
    companion object {
        const val API_VISION_URL = "https://vision.googleapis.com"
    }

    @POST("v1/images:annotate?key=AIzaSyAoRgrA-pmSmIhmWlV0BkB1VMZJtSARZQ4")
    fun getVisionResults(@Body requestBody: VisionRequestBody): Observable<VisionResponses>
}
