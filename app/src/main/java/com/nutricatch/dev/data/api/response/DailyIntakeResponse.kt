package com.nutricatch.dev.data.api.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class DailyIntakeResponse(

	@field:SerializedName("carbohydrates")
	val carbohydrates: String? = null,

	@field:SerializedName("maxSodium")
	val maxSodium: String? = null,

	@field:SerializedName("fats")
	val fats: String? = null,

	@field:SerializedName("minFiber")
	val minFiber: String? = null,

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("maxSugar")
	val maxSugar: String? = null,

	@field:SerializedName("calories")
	val calories: String? = null
)
