package com.findly.application.data.googleApi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by patrykgalazka on 20.04.2018.
 */
class VisionResponse {
    @SerializedName("webDetection")
    @Expose
    var webDetection: VisionWebDetection? = null
}