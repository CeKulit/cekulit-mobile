package com.bangkit.cekulit.data

import androidx.lifecycle.LiveData
import com.bangkit.cekulit.data.response.Story
import com.bangkit.cekulit.data.room.ProductsDao

class ProductRepository(private val productsDao: ProductsDao) {

    fun getListFavProducts(): LiveData<List<Story>> {
        return productsDao.getListFavProducts()
    }

    fun getProductById(id: String): LiveData<Story>{
        return productsDao.getProductById(id)
    }

    suspend fun setFavProduct(products: List<Story>){
        productsDao.insertProduct(products)
    }

    suspend fun deleteProductById(id: String){
        productsDao.deleteById(id)
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(
            productsDao: ProductsDao
        ): ProductRepository =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(productsDao)
            }.also { instance = it }
    }
}