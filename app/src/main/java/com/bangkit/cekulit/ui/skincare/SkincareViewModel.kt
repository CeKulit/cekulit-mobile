package com.bangkit.cekulit.ui.skincare

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.response.ListSkincareResponse
import com.bangkit.cekulit.data.response.ProfileResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class SkincareViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _daySkincare = MutableLiveData<List<ListSkincareResponse>>()
    val daySkincare: LiveData<List<ListSkincareResponse>> = _daySkincare
    private val _day = MutableLiveData<String>()
    val day: LiveData<String> = _day

    private val _nightSkincare = MutableLiveData<List<ListSkincareResponse>>()
    val nightSkincare: LiveData<List<ListSkincareResponse>> = _nightSkincare
    private val _night = MutableLiveData<String>()
    val night: LiveData<String> = _night

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    

    val authToken: LiveData<String> = authRepository.getSession().asLiveData()

    fun getDaySkincare(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService(":3000").getListDaySkincare()
                _isLoading.value = false
                _day.value = "day"
                _daySkincare.value = response

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
            }
            catch (e: HttpException){
                _isLoading.value = false
                Log.e("getDaySkincare", e.message.toString())
            }
        }

    }

    fun getNightSkincare(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService(":3000").getListNightSkincare()
                _isLoading.value = false
                _night.value = "night"
                _nightSkincare.value = response

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
            }
            catch (e: HttpException){
                _isLoading.value = false
                Log.e("getDaySkincare", e.message.toString())
            }
        }

    }
    
}