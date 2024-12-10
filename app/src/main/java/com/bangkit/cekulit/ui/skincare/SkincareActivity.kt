package com.bangkit.cekulit.ui.skincare

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.cekulit.data.response.ListSkincareResponse
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.databinding.ActivitySkincareBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.camera.CameraActivity.Companion.EXTRA_RESULT_ANALYSIS

class SkincareActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySkincareBinding

    private val skincareViewModel: SkincareViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkincareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        skincareViewModel.authToken.observe(this){ token ->
            if(!token.isNullOrEmpty()){
                skincareViewModel.getDaySkincare()
                skincareViewModel.getNightSkincare()
            }
        }

        skincareViewModel.daySkincare.observe(this){ skincare ->
            if(skincare != null){
                skincareViewModel.day.observe(this){
                    showDaySkincare(skincare, it)
                }
            }
        }

        skincareViewModel.nightSkincare.observe(this){ skincare ->
            if(skincare != null){
                skincareViewModel.night.observe(this){
                    showNightSkincare(skincare, it)
                }
            }
        }

    }

    private fun showDaySkincare(skincare: List<ListSkincareResponse>, timeSkincare: String) {
        if (skincare.isNotEmpty()) {
            val resultAnalysis = intent.getStringExtra(EXTRA_RESULT_ANALYSIS)
            val adapter = SkincareAdapter(resultAnalysis!!, timeSkincare)
            adapter.submitList(skincare)
            binding.tvResult.text = resultAnalysis
            binding.rvMorningSkincare.adapter = adapter
            binding.rvMorningSkincare.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showNightSkincare(skincare: List<ListSkincareResponse>, timeSkincare: String) {
        if (skincare.isNotEmpty()) {
            val resultAnalysis = intent.getStringExtra(EXTRA_RESULT_ANALYSIS)
            val adapter = SkincareAdapter(resultAnalysis!!, timeSkincare)
            adapter.submitList(skincare)
            binding.rvNightSkincare.adapter = adapter
            binding.rvNightSkincare.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        }
    }
}