package com.bangkit.cekulit.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class DetailProductResponse(
	@PrimaryKey
	@SerializedName("desc")
	val desc: String,
	@SerializedName("photoUrl")
	val photoUrl: String? = null,
	@SerializedName("title")
	val title: String? = null
)

