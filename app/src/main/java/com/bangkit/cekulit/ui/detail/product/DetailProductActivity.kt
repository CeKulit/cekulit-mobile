package com.bangkit.cekulit.ui.detail.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.cekulit.R
import com.bangkit.cekulit.data.response.DetailProductResponse
import com.bangkit.cekulit.databinding.ActivityDetailProductBinding
import com.bangkit.cekulit.ui.ViewModelFactory
import com.bangkit.cekulit.ui.auth.login.LoginActivity
import com.bumptech.glide.Glide

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding


    private val detailViewModel by viewModels<DetailProductViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var isFav : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoProduct = intent.getStringExtra(PHOTO_PRODUCT)
        val titleProduct = intent.getStringExtra(TITLE_PRODUCT)
        val descProduct = intent.getStringExtra(DESC_PRODUCT)

        val detailProduct = DetailProductResponse(
            photoUrl = photoProduct,
            title = titleProduct,
            desc = descProduct!!
        )


        Glide.with(binding.ivDetailPhoto.context)
            .load(photoProduct)
            .into(binding.ivDetailPhoto)
        binding.apply {
            tvDetailName.text = title
            tvDetailDescription.text = descProduct
            fabFavorite.setOnClickListener {
                if (isFav) {
                    Toast.makeText(this@DetailProductActivity, R.string.toast_delete_fav, Toast.LENGTH_SHORT).show()
                    detailViewModel.deleteProduct(descProduct)
                    isFav = false
                } else {
                    Toast.makeText(this@DetailProductActivity, R.string.toast_add_fav, Toast.LENGTH_SHORT).show()
                    detailViewModel.saveProduct(listOf(detailProduct))
                    isFav = true
                }
            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }


        detailViewModel.getProductByDesc(descProduct).observe(this) { result ->
            if (result != null) {
                isFav = true
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_fill)
            } else {
                isFav = false
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
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
        const val PHOTO_PRODUCT = "photo_product"
        const val TITLE_PRODUCT = "title_product"
        const val DESC_PRODUCT = "desc_product"
    }
}