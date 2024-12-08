package com.bangkit.cekulit.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bangkit.cekulit.databinding.ActivityResultBinding
import com.bangkit.cekulit.ui.camera.CameraActivity
import com.bangkit.cekulit.ui.skincare.SkincareActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentImageUri = intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()

        showImage()

        binding.btnRoutine.setOnClickListener {
            startActivity(Intent(this@ResultActivity, SkincareActivity::class.java))
        }

    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivResultPhoto.setImageURI(it)
        }
    }

}