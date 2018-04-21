package com.findly.application.cameraActivity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Surface
import android.view.TextureView
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.application.confirmActivity.ConfirmActivity
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

class CameraActivity : BaseActivity(), CameraActivityContract.View {

    companion object {
        const val PERMISSIONS_CODE = 100
    }

    var cameraCaptureSessions: CameraCaptureSession? = null
    var captureRequestBuilder: CaptureRequest.Builder? = null
    var cameraDevice: CameraDevice? = null
    private var imageDimension: android.util.Size? = null
    var mBackgroundHandler: Handler? = null
    private val REQUEST_CAMERA_PERMISSION = 200
    var textureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {

        }

        override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
        }

        override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
            return false
        }

        override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
            openCamera()
        }

    }

    private fun openCamera() {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            if (startCamera(manager)) return
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun startCamera(manager: CameraManager): Boolean {
        imageDimension = manager.getCameraCharacteristics(manager.cameraIdList[0])
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
                .getOutputSizes(SurfaceTexture::class.java)[0]
        if (isApplicationAccesedToCamera() && isApplicationAccessedToWriteInStorage()) {
            requestPermissions()
            return true
        }
        manager.openCamera(manager.cameraIdList.component1(), stateCallback, null)
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
    }

    private fun isApplicationAccessedToWriteInStorage() =
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)

    private fun isApplicationAccesedToCamera() =
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED)

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

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice?.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice?.close()
            cameraDevice = null
        }
    }

    private fun createCameraPreview() {
        try {
            with(activityCameraPreviewTv.surfaceTexture) {
                imageDimension?.height?.let { this.setDefaultBufferSize(imageDimension!!.width, it) }
                val surface = Surface(this)
                captureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                captureRequestBuilder?.addTarget(surface)
                cameraDevice?.createCaptureSession(Arrays.asList(surface), object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (null == cameraDevice) {
                            return
                        }
                        cameraCaptureSessions = cameraCaptureSession
                        try {
                            updatePreview()
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {}
                }, null)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @Throws(CameraAccessException::class)
    private fun updatePreview() {
        captureRequestBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        cameraCaptureSessions?.setRepeatingRequest(captureRequestBuilder?.build(), null, mBackgroundHandler)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        checkPermission()
        setupLayout()
    }

    private fun checkPermission() {
        if (isPermissionGranted())
            initCamera()
        else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSIONS_CODE)
    }

    private fun setupLayout() {
        activityCameraTakePictureBtn.setOnClickListener { takePicture() }
        activityCameraGaleryPickBtn.setOnClickListener { pickPicture() }
        activityCameraBackIv.setOnClickListener { onBackPressed() }
    }

    private fun pickPicture() {
        startActivityForResult(Intent(Intent.ACTION_PICK).apply { type = "image/*" },
                200)
    }

    private fun isPermissionGranted(): Boolean =
            (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_CODE)
            initCamera()
        else
            this.finish()
    }

    private fun initCamera() {
        activityCameraPreviewTv.surfaceTextureListener = textureListener;
    }

    override fun takePicture() {
        startActivity(getConfirmActivityIntent(activityCameraPreviewTv.bitmap))
    }

    private fun getConfirmActivityIntent(bitmap: Bitmap): Intent {
        return Intent(this, ConfirmActivity::class.java)
                .apply {
                    putExtra("image", bitmap
                            .createImageFromBitmap())
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                startActivity(getConfirmActivityIntent(BitmapFactory
                        .decodeStream(contentResolver.openInputStream(data?.data))))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}