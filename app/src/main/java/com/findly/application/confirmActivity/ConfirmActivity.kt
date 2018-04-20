package com.findly.application.confirmActivity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.findly.R
import com.findly.application.GlideApp
import com.findly.application.data.googleApi.VisionApiService
import com.findly.application.data.googleApi.model.VisionRequest
import com.findly.application.data.googleApi.model.VisionRequestBody
import com.findly.application.data.googleApi.model.VisionRequestFeature
import com.findly.application.data.googleApi.model.VisionRequestImage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_confirm.*


class ConfirmActivity : AppCompatActivity() {
    lateinit var image: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        setupImageFromSharedIntent()
        setupLayout()
    }

    private fun setupLayout() {
        GlideApp.with(this).load(image).into(activityConfirmResult)
        activityConfirmResultSend.setOnClickListener { uploadPictureToVisionApi() }
    }

    private fun setupImageFromSharedIntent() {
        intent.getStringExtra("image")?.let {
            image = getImageBitmap()
        }
        (intent.getParcelableExtra(Intent.EXTRA_STREAM) as Uri?)?.let {
            image = MediaStore.Images.Media.getBitmap(this.contentResolver, it)
        }

    }

    private fun uploadPictureToVisionApi() {
        VisionApiService.getApiService()
                .getVisionResults(setUpVisionRequest(image))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.e("działa", "tak")
                        },
                        {

                        }
                )
    }

    private fun getImageBitmap() = BitmapFactory.decodeStream(this
            .openFileInput(intent.getStringExtra("image")))

    private fun setUpVisionRequest(bitmap: Bitmap): VisionRequestBody {
        return VisionRequestBody(mutableListOf(VisionRequest(VisionRequestImage(bitmap)
                , mutableListOf(VisionRequestFeature("WEB_DETECTION")))))
    }

}
