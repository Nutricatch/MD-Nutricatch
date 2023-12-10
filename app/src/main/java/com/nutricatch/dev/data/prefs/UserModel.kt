package com.nutricatch.dev.data.prefs

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)