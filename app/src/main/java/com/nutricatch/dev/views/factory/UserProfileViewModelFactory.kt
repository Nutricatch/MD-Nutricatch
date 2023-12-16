package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.repository.UserRepository
import com.nutricatch.dev.views.navigation.profile.ProfileViewModel

class UserProfileViewModelFactory(
    private val preferences: Preferences,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(preferences, userRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: UserProfileViewModelFactory? = null

        fun getInstance(preferences: Preferences, context: Context): UserProfileViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserProfileViewModelFactory(
                    preferences,
                    Injection.provideUserRepository(context)
                )
            }.also { INSTANCE = it }
    }
}