package com.bangkit.cekulit.di

import android.content.Context
import com.bangkit.cekulit.data.pref.UserPreference
import com.bangkit.cekulit.data.pref.dataStore
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.HistoryRepository
import com.bangkit.cekulit.data.ProductRepository
import com.bangkit.cekulit.data.room.history.HistoryDatabase
import com.bangkit.cekulit.data.room.products.ProductsDatabase

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(pref)
    }

    fun provideProductRepository(context: Context): ProductRepository {
        val database = ProductsDatabase.getDatabase(context)
        val dao = database.productsDao()
        return ProductRepository.getInstance(dao)
    }

    fun provideHistoryRepository(context: Context): HistoryRepository {
        val database = HistoryDatabase.getInstance(context)
        val dao = database.historyDao()
        return HistoryRepository.getInstance(dao)
    }
}