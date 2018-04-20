package com.findly.application.data.googleApi.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by patrykgalazka on 20.04.2018.
 */
class VisionWebEntity(
        @SerializedName("entityId")
        @Expose
        var entityId: String,
        @SerializedName("score")
        @Expose
        var score: Double,
        @SerializedName("description")
        @Expose
        var description: String)
