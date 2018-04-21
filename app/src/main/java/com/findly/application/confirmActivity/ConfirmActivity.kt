package com.findly.application.confirmActivity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.findly.R
import com.findly.application.GlideApp
import com.findly.application.base.BaseActivity
import com.findly.application.data.googleApi.VisionApiService
import com.findly.application.data.googleApi.model.VisionRequest
import com.findly.application.data.googleApi.model.VisionRequestBody
import com.findly.application.data.googleApi.model.VisionRequestFeature
import com.findly.application.data.googleApi.model.VisionRequestImage
import com.findly.application.results.ResultsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_confirm.*
import java.io.ByteArrayOutputStream
import java.util.*

class ConfirmActivity : BaseActivity() {
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
        activityConfirmCloseIv.setOnClickListener { onBackPressed() }
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
                            val webDetectionResponses = emptyList<String>().toMutableList()
                            it.responses.forEach {
                                it.webDetection?.bestGuessedLabels?.forEach { bestGuessed ->
                                    webDetectionResponses.add(bestGuessed.label)
                                }
                                it.webDetection?.webEntities?.forEach {
                                    webDetectionResponses.add(it.description)
                                }
                            }
                            var intent = Intent(this, ResultsActivity::class.java)
                            intent.apply {
                                putStringArrayListExtra("results", webDetectionResponses as ArrayList<String>)
                                putExtra("image", image.createImageFromBitmap())
                            }
                            startActivity(intent)
                        }, {
                })
    }

    private fun getImageBitmap() = BitmapFactory.decodeStream(this
            .openFileInput(intent.getStringExtra("image")))

    private fun setUpVisionRequest(bitmap: Bitmap): VisionRequestBody {
        return VisionRequestBody(mutableListOf(VisionRequest(VisionRequestImage(bitmap)
                , mutableListOf(VisionRequestFeature("WEB_DETECTION")))))
    }

    fun Bitmap.createImageFromBitmap(): String? {
        var fileName: String? = "myImage"
        try {
            with(ByteArrayOutputStream()) {
                this@createImageFromBitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                openFileOutput(fileName, Context.MODE_PRIVATE).run {
                    write(this@with.toByteArray())
                    close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fileName = null
        }
        return fileName
    }
}
