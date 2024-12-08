package com.bangkit.cekulit.data.response

import com.google.gson.annotations.SerializedName

data class SkincareResponse (

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null,

    @field:SerializedName("ingredients")
    val ingredients: String? = null,

    @field:SerializedName("step")
    val step: String? = null

)