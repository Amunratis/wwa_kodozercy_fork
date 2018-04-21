package com.findly.application.shareImage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.findly.R
import com.findly.application.GlideApp
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_share_image.*
import java.io.ByteArrayOutputStream


class ShareImageActivity : AppCompatActivity() {
    lateinit var image: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_image)
        readExtrasData()
        setupLayout()
        uploadImage()
    }

    private fun uploadImage() {
        with(ByteArrayOutputStream()) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, this)
            FirebaseStorage.getInstance("gs://hackaton-4a09c.appspot.com")
                    .reference.child("images/${image.hashCode()}$")
                    .putBytes(this.toByteArray())
                    .addOnCompleteListener {
                        it.result.downloadUrl
                    }
                    .addOnFailureListener {
                        it.printStackTrace()
                    }
        }


    }

    private fun setupLayout() {
        GlideApp.with(this).load(image).into(activityShareImageView)
    }

    private fun readExtrasData() {
        intent.getStringExtra("image")?.let {
            image = getImageBitmap()
        }
    }

    private fun getImageBitmap() = BitmapFactory.decodeStream(this
            .openFileInput(intent.getStringExtra("image")))
}
