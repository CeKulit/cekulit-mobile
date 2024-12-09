package com.bangkit.cekulit.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cekulit.data.ProductRepository
import com.bangkit.cekulit.data.response.DetailProductResponse

class FavoriteViewModel(private val productRepository: ProductRepository) : ViewModel() {
    fun getListFavProducts(): LiveData<List<DetailProductResponse>> = productRepository.getListFavProducts()
}