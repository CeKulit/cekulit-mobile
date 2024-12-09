package com.bangkit.cekulit.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bangkit.cekulit.databinding.ActivityResultBinding
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.EXTRA_CAMERAX_IMAGE
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.EXTRA_RESULT_ANALYSIS
import com.bangkit.cekulit.ui.skincare.SkincareActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private var currentImageUri: Uri? = null
    private var resultAnalysis: String? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentImageUri = intent.getStringExtra(EXTRA_CAMERAX_IMAGE)?.toUri()
        resultAnalysis = intent.getStringExtra(EXTRA_RESULT_ANALYSIS)

        showImage()

        binding.btnRoutine.setOnClickListener {
            val intent = Intent(this@ResultActivity, SkincareActivity::class.java)
            intent.putExtra(EXTRA_RESULT_ANALYSIS, resultAnalysis)
            startActivity(intent)
        }

        resultAnalysis?.let {
            binding.tvResult.text = it
        }

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivResultPhoto.setImageURI(it)
        }
    }

}