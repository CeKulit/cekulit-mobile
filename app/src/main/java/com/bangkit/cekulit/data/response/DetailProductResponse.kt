package com.bangkit.cekulit.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class DetailProductResponse(

	@PrimaryKey
	@field:SerializedName("desc")
	val desc: String,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster")
	val poster: String,

	@field:SerializedName("stars")
	val stars: String,

	@field:SerializedName("reviews_count")
	val reviewsCount: String,

	@field:SerializedName("link")
	val link: String,

)
