package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

enum class ActivityLevel(val value: String) {
    SEDENTARY("SEDENTARY"),
    MODERATELY_ACTIVE("MODERATELY_ACTIVE"),
    VERY_ACTIVE("VERY_ACTIVE")
}

enum class FitnessGoal(val value:String){
    WEIGHTLOSS("WeightLoss"),
    MAINTENANCE("Maintenance"),
    WEIGHTGAIN("WeightGain")
}

data class HealthResponse(

    @field:SerializedName("fitnessGoal")
    val fitnessGoal: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("weight")
    val weight: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("age")
    val age: Int? = null,

    @field:SerializedName("activityLevel")
    val activityLevel: String? = null,

    @field:SerializedName("height")
    val height: Int? = null
)
