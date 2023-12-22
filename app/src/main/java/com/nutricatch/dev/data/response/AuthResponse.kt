package com.nutricatch.dev.data.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

