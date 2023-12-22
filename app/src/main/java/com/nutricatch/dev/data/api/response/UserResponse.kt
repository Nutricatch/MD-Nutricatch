package com.nutricatch.dev.data.api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("userId")
    val id: Int? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    )