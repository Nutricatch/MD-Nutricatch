package com.nutricatch.dev.views.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.views.loading.LoadingViewModel
import com.nutricatch.dev.views.navigation.profile.ProfileViewModel

class PreferencesViewModelFactory(private val preferences: Preferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoadingViewModel::class.java) -> {
                return LoadingViewModel(preferences) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(preferences) as T
            }
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferencesViewModelFactory? = null

        fun getInstance(preferences: Preferences): PreferencesViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesViewModelFactory(preferences)
            }.also { INSTANCE = it }
    }
}