package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

data class ConsumeResponse(

    @field:SerializedName("foodName")
    val foodName: String? = null,

    @field:SerializedName("carbohydrates")
    val carbohydrates: Double? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("fiber")
    val fiber: Double? = null,

    @field:SerializedName("salt")
    val salt: Double? = null,

    @field:SerializedName("protein")
    val protein: Double? = null,

    @field:SerializedName("fat")
    val fat: Double? = null,

    @field:SerializedName("healthId")
    val healthId: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("calories")
    val calories: Double? = null,

    @field:SerializedName("sugar")
    val sugar: Double? = null
)
