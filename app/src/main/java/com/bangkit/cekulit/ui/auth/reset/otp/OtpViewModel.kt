package com.bangkit.cekulit.ui.auth.reset.otp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.response.MessageResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class OtpViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isSuccess = MutableLiveData<MessageResponse>()
    val isSuccess: LiveData<MessageResponse> = _isSuccess
    private val _responseOtp = MutableLiveData<String>()
    val responseOtp: LiveData<String> = _responseOtp

    fun insertOtpUser(email: String, otp: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().otp(email, otp)
                _isLoading.value = false
                _isSuccess.value = response

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
                _responseOtp.value = "Request timed out!"
            }
            catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
                _responseOtp.value = errorBody.message!!

            }
        }

    }
}