package com.findly.application.data.googleApi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by patrykgalazka on 21.04.2018.
 */
data class VisionBestGuessedResult(
        @Expose
        @SerializedName("label")
        var label: String)