package com.bangkit.cekulit.data.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("data")
    var data: Data = Data()
)

data class Data(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("result")
    var result: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("confidenceScore")
    var confidenceScore: Double? = null,
    @SerializedName("isAboveThreshold")
    var isAboveThreshold: Boolean? = null,
    @SerializedName("createdAt")
    var createdAt: String? = null
)
