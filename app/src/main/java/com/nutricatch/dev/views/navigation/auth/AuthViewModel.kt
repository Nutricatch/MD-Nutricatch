package com.nutricatch.dev.views.navigation.auth

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.prefs.UserModel
import com.nutricatch.dev.data.repository.UserRepository

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)

    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)

    suspend fun saveSession(userModel: UserModel) {
        return repository.saveSession(userModel)
    }
}