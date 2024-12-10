package com.bangkit.cekulit.data

import androidx.lifecycle.LiveData
import com.bangkit.cekulit.data.response.DetailProductResponse
import com.bangkit.cekulit.data.room.products.ProductsDao

class ProductRepository(private val productsDao: ProductsDao) {

    fun getListFavProducts(): LiveData<List<DetailProductResponse>> {
        return productsDao.getListFavProducts()
    }

    fun getProductByDesc(desc: String): LiveData<DetailProductResponse>{
        return productsDao.getProductByDesc(desc)
    }

    suspend fun setFavProduct(products: List<DetailProductResponse>){
        productsDao.insertProduct(products)
    }

    suspend fun deleteProductByDesc(desc: String){
        productsDao.deleteByDesc(desc)
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