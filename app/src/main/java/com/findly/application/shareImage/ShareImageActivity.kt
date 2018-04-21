package com.findly.application.shareImage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.findly.R
import com.findly.application.GlideApp
import com.findly.data.firebase.Database
import com.findly.data.firebase.model.Post
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_share_image.*


class ShareImageActivity : AppCompatActivity() {
    lateinit var image: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_image)
        readExtrasData()
        setupLayout()
    }

    private fun getCompleteListener() = OnCompleteListener<UploadTask.TaskSnapshot> { taskSnapshot -> uploadPost(taskSnapshot.result.downloadUrl.toString()) }

    private fun setupLayout() {
        GlideApp.with(this).load(image).into(activityShareImageView)
        activityShareAddFab.setOnClickListener { Database.uploadImage(image, getCompleteListener()) }
    }

    private fun uploadPost(imageUrl: String) {
        Database.addPost(Post().apply {
            this.userName = "patryk"
            this.imageUrl = imageUrl
            this.description = activityShareDescription.text.toString()
        }, OnCompleteListener {
            Log.e("item", "dodany")
        })
    }

    private fun readExtrasData() {
        intent.getStringExtra("image")?.let {
            image = getImageBitmap()
        }
    }

    private fun getImageBitmap() = BitmapFactory.decodeStream(this
            .openFileInput(intent.getStringExtra("image")))
}
