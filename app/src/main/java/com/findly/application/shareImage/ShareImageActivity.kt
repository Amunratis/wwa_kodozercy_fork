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
import com.google.firebase.auth.FirebaseAuth
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
        activityShareAddFab.setOnClickListener { sendPost() }
    }

    private fun sendPost() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener {
            uploadImage(image, getCompleteListener())
        }.addOnFailureListener {
                    Log.e("Login", "error")
                }
    }

    private fun uploadImage(bitmap: Bitmap, completeListener: OnCompleteListener<UploadTask.TaskSnapshot>) {
        with(ByteArrayOutputStream()) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, this)
            FirebaseStorage.getInstance()
                    .reference.child("images/${bitmap.hashCode()}.jpg")
                    .putBytes(this.toByteArray())
                    .addOnCompleteListener(
                            completeListener
                    )
                    .addOnFailureListener {
                        it.printStackTrace()
                    }
        }


    }

    private fun getCompleteListener() = OnCompleteListener<UploadTask.TaskSnapshot> { taskSnapshot ->
        Database.addPost(Post().apply {
            userName = "patryk"
            description = activityShareDescription.text.toString()
            imageUrl = taskSnapshot.result.downloadUrl.toString()
        }, OnCompleteListener {})
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
