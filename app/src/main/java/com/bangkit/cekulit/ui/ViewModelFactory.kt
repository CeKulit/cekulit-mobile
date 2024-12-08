package com.bangkit.cekulit.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cekulit.data.AuthRepository
import com.bangkit.cekulit.data.ProductRepository
import com.bangkit.cekulit.di.Injection
import com.bangkit.cekulit.ui.auth.login.LoginViewModel
import com.bangkit.cekulit.ui.detail.product.DetailProductViewModel
import com.bangkit.cekulit.ui.favorite.FavoriteViewModel
import com.bangkit.cekulit.ui.home.HomeViewModel
import com.bangkit.cekulit.ui.setting.SettingViewModel
import com.bangkit.cekulit.ui.skincare.SkincareViewModel

class ViewModelFactory(private val authRepository: AuthRepository, private val productRepository: ProductRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(DetailProductViewModel::class.java) -> {
                DetailProductViewModel(authRepository, productRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(productRepository) as T
            }
            modelClass.isAssignableFrom(SkincareViewModel::class.java) -> {
                SkincareViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideAuthRepository(context), Injection.provideProductRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

}