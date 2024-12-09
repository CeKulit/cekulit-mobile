package com.bangkit.cekulit.ui.detail.skincare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.response.DetailSkincareResponse
import com.bangkit.cekulit.data.response.Skincare
import com.bangkit.cekulit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DetailSkincareViewModel: ViewModel() {
    private val _skincare = MutableLiveData<Map<String, Skincare?>>()
    val skincare: LiveData<Map<String, Skincare?>> = _skincare
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getDetailProduct(type: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService(":3000").getDetailSkincare(type)
                _isLoading.value = false

                _skincare.value = response.toMap()

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
}