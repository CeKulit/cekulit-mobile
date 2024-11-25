package com.dicoding.cekulit.di

import android.content.Context
import com.dicoding.cekulit.data.pref.UserPreference
import com.dicoding.cekulit.data.pref.dataStore
import com.dicoding.cekulit.data.remote.AuthRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(pref)
    }
}