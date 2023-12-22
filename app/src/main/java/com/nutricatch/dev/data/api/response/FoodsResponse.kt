package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

data class FoodsResponse(

    @field:SerializedName("FoodsResponse")
    val foodsResponse: List<FoodsResponseItem?>? = null
)

data class FoodsResponseItem(

    @field:SerializedName("sodium")
    val sodium: Double? = null,

    @field:SerializedName("glukosa")
    val sugar: Double? = null,

    @field:SerializedName("fibers")
    val fibers: Double? = null,

    @field:SerializedName("carbs")
    val carbs: Double? = null,

    @field:SerializedName("portion")
    val portion: Double? = null,

    @field:SerializedName("protein")
    val protein: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("fat")
    val fat: Double? = null,

    @field:SerializedName("calories")
    val calories: Double? = null
)
