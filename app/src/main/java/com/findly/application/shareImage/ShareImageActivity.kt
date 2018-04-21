package com.findly.application.shareImage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.findly.R
import com.findly.application.GlideApp
import com.findly.application.main.MainActivity
import com.findly.application.view.FullScreenLoadingDialog
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
    lateinit var loadingDialog: FullScreenLoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_image)
        loadingDialog = FullScreenLoadingDialog.newProgressDialogInstance(this)
        readExtrasData()
        setupLayout()
        activityShareImageShareTv.setOnClickListener { sendPost() }
    }

    private fun sendPost() {
        loadingDialog.show()
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
        }, OnCompleteListener { openMain() })
    }

    private fun openMain() {
        loadingDialog.hide()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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
