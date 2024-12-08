package com.bangkit.cekulit.ui.skincare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.data.response.ProfileResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class SkincareViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _skincare = MutableLiveData<List<ProductResponseItem>>()
    val skincare: LiveData<List<ProductResponseItem>> = _skincare
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    

    val authToken: LiveData<String> = authRepository.getSession().asLiveData()

    fun getSkincare(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService(":4000").getProducts("Bearer ${authToken.value!!}")
                _isLoading.value = false

                _skincare.value = response
            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
            }
            catch (e: HttpException){
                _isLoading.value = false
                Log.e("getSkincare", e.message.toString())
            }
        }

    }
    
}