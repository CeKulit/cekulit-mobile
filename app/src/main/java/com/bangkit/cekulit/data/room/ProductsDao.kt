package com.bangkit.cekulit.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.cekulit.data.response.Story

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: List<Story>)

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: String): LiveData<Story>

    @Query("SELECT * FROM products")
    fun getListFavProducts(): LiveData<List<Story>>

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteById(id: String)
}