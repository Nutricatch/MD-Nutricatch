package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName


data class DailyIntakeResponse(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Double? = null,

	@field:SerializedName("maxSodium")
	val maxSodium: Double? = null,

	@field:SerializedName("fats")
	val fats: Double? = null,

	@field:SerializedName("minFiber")
	val minFiber: Double? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("maxSugar")
	val maxSugar: Double? = null,

	@field:SerializedName("calories")
	val calories: Double? = null
)
