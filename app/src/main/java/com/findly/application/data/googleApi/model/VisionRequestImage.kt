package com.findly.application.data.googleApi.model

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * Created by patrykgalazka on 20.04.2018.
 */
class VisionRequestImage(content: Bitmap) {
    internal var content: String

    init {
        val baos = ByteArrayOutputStream()
        content.compress(Bitmap.CompressFormat.JPEG, 100, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        this.content = Base64.encodeToString(b, Base64.DEFAULT)
    }
}
