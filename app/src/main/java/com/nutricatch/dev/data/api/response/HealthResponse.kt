package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

enum class ActivityLevel(val label: String) {
    SEDENTARY("Sedentary"),
    MODERATELY_ACTIVE("Moderately Active"),
    VERY_ACTIVE("Very Active")
}

enum class FitnessGoal {
    WeightGain,
    Maintenance,
    WeightLoss
}

enum class Gender {
    MALE, FEMALE
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
