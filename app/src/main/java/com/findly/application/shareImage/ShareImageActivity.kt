package com.findly.application.shareImage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.findly.R
import com.findly.application.GlideApp
import com.findly.data.firebase.Database
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_share_image.*
import java.io.ByteArrayOutputStream


class ShareImageActivity : AppCompatActivity() {
    lateinit var image: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_image)
        readExtrasData()
        setupLayout()
        Database.uploadImage(image, getCompleteListener())
    }

    private fun getCompleteListener() = OnCompleteListener<UploadTask.TaskSnapshot> { taskSnapshot -> taskSnapshot.result.downloadUrl }

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
