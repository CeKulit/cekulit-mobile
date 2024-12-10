package com.bangkit.cekulit.ui.detail.skincare

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.cekulit.databinding.ActivityDetailSkincareBinding
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.EXTRA_RESULT_ANALYSIS
import com.bumptech.glide.Glide

class DetailSkincareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSkincareBinding


    private val detailViewModel by viewModels<DetailSkincareViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSkincareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleSkincare = intent.getStringExtra(TITLE_SKINCARE)

        val typeSkinResult = intent.getStringExtra(EXTRA_RESULT_ANALYSIS)

        val timeSkincare = intent.getStringExtra(TIME_SKINCARE)


        titleSkincare?.let {
            detailViewModel.getDetailProduct(typeSkinResult!!, timeSkincare!!)
            setupView(titleSkincare)
        }


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setupView(title: String) {
        detailViewModel.skincare.observe(this) { skincare ->
            val selectedItem = skincare[title]
            selectedItem?.let { item ->
                Glide.with(binding.ivDetailPhoto.context)
                    .load(item.photoUrl)
                    .into(binding.ivDetailPhoto)
                binding.apply {
                    tvDetailName.text = item.title
                    tvDetailDescription.text = item.desc
                }
            }
        }
    }


    private fun showLoading(isLoading: Boolean){
        if(isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return false
    }

    companion object {
        const val TITLE_SKINCARE = "title"
        const val TIME_SKINCARE = "time"
    }
}