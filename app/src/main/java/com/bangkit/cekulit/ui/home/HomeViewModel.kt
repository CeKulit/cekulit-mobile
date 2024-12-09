package com.bangkit.cekulit.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.response.MessageResponse
import com.bangkit.cekulit.data.response.ProductResponseItem
import com.bangkit.cekulit.data.response.ProfileResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class HomeViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _product = MutableLiveData<List<ProductResponseItem>>()
    val product: LiveData<List<ProductResponseItem>> = _product
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _responseProfile = MutableLiveData<String>()
    val responseProfile: LiveData<String> = _responseProfile
    private val _responseProducts = MutableLiveData<String>()
    val responseProducts: LiveData<String> = _responseProducts
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    val authToken: LiveData<String> = authRepository.getSession().asLiveData()

    private val _profile = MutableLiveData<ProfileResponse>()
    val profile: LiveData<ProfileResponse> = _profile

    fun getProducts(query: String? = null){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(":3000").getProducts("Bearer ${authToken.value}", query = query)
                _isLoading.value = false

                _product.value = response

                _isEmpty.value = response.isEmpty()

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
                _responseProducts.value = "Request timed out!"
            }
            catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
                _responseProducts.value = errorBody.message!!

            }
        }

    }

    fun getProfile(){
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService(":3000").getProfile("Bearer ${authToken.value!!}")
                Log.e("PROFILE", "$response" )
                _profile.value = response
            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
            }
            catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
                _responseProfile.value = errorBody.message!!
            }
        }

    }

    fun showFilter(query: String?){
        getProducts(query)
    }
}