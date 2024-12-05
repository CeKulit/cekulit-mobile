package com.bangkit.cekulit.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.cekulit.data.response.Story

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class ProductsDatabase: RoomDatabase() {
    abstract fun productsDao(): ProductsDao


    companion object {
        @Volatile
        private var INSTANCE: ProductsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ProductsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductsDatabase::class.java, "Products.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}