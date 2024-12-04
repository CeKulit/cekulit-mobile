package com.bangkit.cekulit.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.AuthRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

}