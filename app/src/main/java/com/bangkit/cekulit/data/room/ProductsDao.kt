package com.bangkit.cekulit.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.cekulit.data.response.DetailProductResponse

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: List<DetailProductResponse>)

    @Query("SELECT * FROM products WHERE `desc` = :id")
    fun getProductByDesc(id: String): LiveData<DetailProductResponse>

    @Query("SELECT * FROM products")
    fun getListFavProducts(): LiveData<List<DetailProductResponse>>

    @Query("DELETE FROM products WHERE `desc` = :id")
    suspend fun deleteByDesc(id: String)
}