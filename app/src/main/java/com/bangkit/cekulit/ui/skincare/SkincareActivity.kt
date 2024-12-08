package com.bangkit.cekulit.ui.skincare

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.databinding.ActivitySkincareBinding
import com.bangkit.cekulit.ui.ViewModelFactory

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
                skincareViewModel.getSkincare()
            }
        }

        skincareViewModel.skincare.observe(this){
            showSkincare(it)
        }

    }

    private fun showSkincare(skincare: List<ProductResponseItem>) {
        if (skincare.isNotEmpty()) {
            val adapter = SkincareAdapter()
            adapter.submitList(skincare)
            binding.rvSkincare.adapter = adapter
            binding.rvSkincare.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}