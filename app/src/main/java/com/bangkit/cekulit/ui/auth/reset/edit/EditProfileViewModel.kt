package com.bangkit.cekulit.ui.auth.reset.edit

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.response.MessageResponse
import com.bangkit.cekulit.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class EditProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _editProfile = MutableLiveData<MessageResponse>()
    val editProfile: LiveData<MessageResponse> = _editProfile
    private val _responseEdit = MutableLiveData<String>()
    val responseEdit: LiveData<String> = _responseEdit
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val authToken: LiveData<String> = repository.getSession().asLiveData()



    @SuppressLint("SuspiciousIndentation")
    fun editProfile(name: String, age: String, gender: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().editProfile("Bearer ${authToken.value}", name, age, gender)
                _isLoading.value = false

                _editProfile.value = response

            }
            catch (e: SocketTimeoutException) {
                Log.e("API_ERROR", "Request timed out!")
                _responseEdit.value = "Request timed out!"
            }
            catch (e: HttpException){
                _isLoading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, MessageResponse::class.java)
                _responseEdit.value = errorBody.message!!
            }
        }

    }
}