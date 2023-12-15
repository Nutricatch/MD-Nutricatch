package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.UserRepository
import com.nutricatch.dev.views.navigation.auth.AuthViewModel

class AuthViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthViewModelFactory? = null

        fun getInstance(context: Context): AuthViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthViewModelFactory(Injection.provideAuthRepository(context))
            }.also { INSTANCE = it }
    }
}