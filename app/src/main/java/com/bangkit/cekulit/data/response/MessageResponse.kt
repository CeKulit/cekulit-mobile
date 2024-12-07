package com.bangkit.cekulit.data.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @field:SerializedName("message")
    val message: String? = null
)
