package com.bangkit.cekulit.ui.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bangkit.cekulit.databinding.ActivityResultBinding
import com.bangkit.cekulit.ui.camera.CameraActivity
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.CAMERAX_RESULT

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivResultPhoto.setImageURI(it)
        }
    }

}