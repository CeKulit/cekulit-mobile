package com.bangkit.cekulit.ui.auth.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.retrofit.ApiConfig
import com.bangkit.cekulit.data.response.LoginResult
import com.bangkit.cekulit.data.response.MessageResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _userLogin = MutableLiveData<LoginResult>()
    val userLogin: LiveData<LoginResult> = _userLogin
    private val _responseLogin = MutableLiveData<String>()
    val responseLogin: LiveData<String> = _responseLogin
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveSession(token: String) {
        viewModelScope.launch {
            repository.saveSession(token)
        }
    }

    val authToken: LiveData<String> = repository.getSession().asLiveData()



    @SuppressLint("SuspiciousIndentation")
    fun loginUser(email: String, password: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().login(email, password)
                _isLoading.value = false

                _userLogin.value = response.loginResult!!

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
                _responseLogin.value = "Request timed out!"
            }
            catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
                _responseLogin.value = errorBody.message!!
            }
        }

    }
}