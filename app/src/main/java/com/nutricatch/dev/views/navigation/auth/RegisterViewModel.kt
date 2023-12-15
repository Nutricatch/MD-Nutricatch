package com.nutricatch.dev.views.navigation.auth

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    fun register (name: String, email: String, password:String) = repository.register(name, email, password)
}