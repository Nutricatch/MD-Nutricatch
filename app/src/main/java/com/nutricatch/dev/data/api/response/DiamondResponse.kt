package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

data class DiamondResponse(

	@field:SerializedName("diamonds")
	val diamonds: Int? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
