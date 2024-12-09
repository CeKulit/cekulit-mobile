package com.bangkit.cekulit.ui.detail.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.ProductRepository
import com.bangkit.cekulit.data.response.DetailProductResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailProductViewModel(private val authRepository: AuthRepository, private val productRepository: ProductRepository): ViewModel() {
    private val _products = MutableLiveData<DetailProductResponse>()
    val products: LiveData<DetailProductResponse> = _products
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val authToken: LiveData<String> = authRepository.getSession().asLiveData()



    fun getDetailProduct(desc: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService(":4000").getDetailProduct("Bearer ${authToken.value!!}", desc)
                _isLoading.value = false

                _products.value = response

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
            }
            catch (e: HttpException){
                _isLoading.value = false
                Log.e("catch", e.message.toString() )

            }
        }

    }

    fun getProductByDesc(desc: String) = productRepository.getProductByDesc(desc)

    fun saveProduct(events: List<DetailProductResponse>) {
        viewModelScope.launch {
            productRepository.setFavProduct(events)
        }
    }

    fun deleteProduct(desc: String) {
        viewModelScope.launch {
            productRepository.deleteProductByDesc(desc)
        }
    }
}