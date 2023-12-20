package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

data class RecommendedNutritionResponse(

	@field:SerializedName("carbohydrates")
	val carbohydrates: String? = null,

	@field:SerializedName("maxSodium")
	val maxSodium: Int? = null,

	@field:SerializedName("fats")
	val fats: String? = null,

	@field:SerializedName("minFiber")
	val minFiber: Int? = null,

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("maxSugar")
	val maxSugar: Int? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)
