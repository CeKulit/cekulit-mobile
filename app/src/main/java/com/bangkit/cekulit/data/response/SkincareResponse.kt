package com.bangkit.cekulit.data.response

import com.google.gson.annotations.SerializedName

data class ListSkincareResponse (

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    )

data class DetailSkincareResponse(
    @SerializedName("Facewash")
    val facewash: Skincare? = null,

    @SerializedName("Cleanser")
    val cleanser: Skincare? = null,

    @SerializedName("Moisturizer")
    val moisturizer: Skincare? = null,

    @SerializedName("Serum")
    val serum: Skincare? = null,

    @SerializedName("Toner")
    val toner: Skincare? = null
) {
    fun toMap(): Map<String, Skincare?> {
        return mapOf(
            "Facewash" to facewash,
            "Cleanser" to cleanser,
            "Moisturizer" to moisturizer,
            "Serum" to serum,
            "Toner" to toner
        )
    }
}

data class Skincare(
    @SerializedName("photoUrl")
    val photoUrl: String? = null,

    @SerializedName("ingredients")
    val ingredients: String? = null,

    @SerializedName("step")
    val step: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("desc")
    val desc: String? = null
)

