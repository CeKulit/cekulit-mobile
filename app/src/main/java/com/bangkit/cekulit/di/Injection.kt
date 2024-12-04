package com.bangkit.cekulit.di

import android.content.Context
import com.bangkit.cekulit.data.pref.UserPreference
import com.bangkit.cekulit.data.pref.dataStore
import com.bangkit.cekulit.data.AuthRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(pref)
    }
}