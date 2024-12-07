package com.bangkit.cekulit.data.response

import com.google.gson.annotations.SerializedName


data class ProductResponseItem(

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster")
	val poster: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)
