package com.nutricatch.dev.views.auth

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.prefs.UserModel
import com.nutricatch.dev.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}