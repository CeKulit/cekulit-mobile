package com.bangkit.cekulit.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bangkit.cekulit.R
import com.bangkit.cekulit.data.response.FileUploadResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import com.bangkit.cekulit.databinding.ActivityCameraBinding
import com.bangkit.cekulit.helper.Utils.createCustomTempFile
import com.bangkit.cekulit.helper.Utils.reduceFileImage
import com.bangkit.cekulit.helper.Utils.uriToFile
import com.bangkit.cekulit.ui.result.ResultActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    private var currentImageUri: Uri? = null

    private var imageCapture: ImageCapture? = null


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.ivSwitchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_FRONT_CAMERA)) CameraSelector.DEFAULT_BACK_CAMERA
            else CameraSelector.DEFAULT_FRONT_CAMERA

            startCamera()
        }

        binding.ivCaptureImage.setOnClickListener {
            takePhotoAndUpload()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //ProcessCameraProvider yang berfungsi untuk mengikat lifecycle kamera ke LifecycleOwner selama jalannya aplikasi
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()


            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    getString(R.string.empty_camera_warning),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhotoAndUpload() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                @RequiresApi(Build.VERSION_CODES.Q)
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    currentImageUri = output.savedUri
                    uploadImageAndAnalysis(currentImageUri)
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(this@CameraActivity, getString(R.string.empty_image_warning), Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    //Di sini kita menggunakan by lazy supaya variabel ini dibuat hanya ketika memang dibutuhkan saja.
    private val orientationEventListener by lazy {
        object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }
                val rotation = when (orientation) {

                    //270 Derajat (Reverse Landscape): Ini adalah orientasi landscape terbalik,
                    //dengan perangkat digunakan dalam posisi mendatar terbalik.
                    in 45 until 135 -> Surface.ROTATION_270

                    //180 Derajat (Reverse Portrait): Ini adalah orientasi potrait terbalik,
                    //dengan perangkat digunakan dalam posisi tegak terbalik.
                    in 135 until 225 -> Surface.ROTATION_180

                    //90 Derajat (Landscape): Ini adalah orientasi landscape standar,
                    //dengan perangkat digunakan dalam posisi mendatar (horizontal).
                    in 225 until 315 -> Surface.ROTATION_90

                    //0 Derajat (Portrait): Ini adalah orientasi potrait standar,
                    //dengan perangkat digunakan dalam posisi tegak (vertikal).
                    else -> Surface.ROTATION_0
                }
                imageCapture?.targetRotation = rotation
            }
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, R.string.toast_permission_granted, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.toast_permission_rejected, Toast.LENGTH_LONG).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uploadImageAndAnalysis(uri: Uri?) {
        uri?.let { imageUri ->
            val imageFile = uriToFile(imageUri, this).reduceFileImage()
            Log.d("Image Classification File", "showImage: ${imageFile.path}")
            showLoading(true)

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService(":4000")
                    val successResponse = apiService.uploadImage(multipartBody)
                    with(successResponse.data) {
                        val intent = Intent(this@CameraActivity, ResultActivity::class.java)
                        if (isAboveThreshold == true) {
                            showToast(successResponse.message.toString())
                        } else {
                            showToast("Model is predicted successfully but under threshold.")
                        }

                        intent.putExtra(EXTRA_CAMERAX_IMAGE, imageUri.toString())
                        intent.putExtra(EXTRA_RESULT_ANALYSIS, "$confidenceScore $result")

                        startActivity(intent)
                        finish()
                    }
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
                    showToast(errorResponse.message.toString())
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onStart() {
        super.onStart()
        orientationEventListener.enable()
    }
    override fun onStop() {
        super.onStop()
        orientationEventListener.disable()
    }


    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val EXTRA_RESULT_ANALYSIS = "Result Analysis"
        const val CAMERAX_RESULT = 200
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}