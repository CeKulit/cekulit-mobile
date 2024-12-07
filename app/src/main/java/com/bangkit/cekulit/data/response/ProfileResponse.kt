package com.bangkit.cekulit.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)
