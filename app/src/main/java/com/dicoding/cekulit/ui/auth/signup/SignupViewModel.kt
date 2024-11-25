package com.dicoding.cekulit.ui.auth.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.cekulit.data.remote.retrofit.ApiConfig
import com.dicoding.cekulit.data.remote.retrofit.response.MessageResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class SignupViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _responseSignup = MutableLiveData<String>()
    val responseSignup: LiveData<String> = _responseSignup
    private val _isSuccess = MutableLiveData<MessageResponse>()
    val isSuccess: LiveData<MessageResponse> = _isSuccess

    fun signupUser(name: String, email: String, password: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().register(name,email, password)
                _isLoading.value = false
                _isSuccess.value = response

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
                _responseSignup.value = "Request timed out!"
            }
            catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
                _responseSignup.value = errorBody.message!!
            }
        }

    }
}