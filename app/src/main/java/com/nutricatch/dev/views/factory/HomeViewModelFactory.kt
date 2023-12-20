package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.repository.DailyIntakeRepository
import com.nutricatch.dev.data.repository.RecommendationRepository
import com.nutricatch.dev.data.repository.UserRepository
import com.nutricatch.dev.views.navigation.home.HomeViewModel

class HomeViewModelFactory(
    private val userRepository: UserRepository,
    private val preferences: Preferences,
    private val dailyIntakeRepository: DailyIntakeRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository, preferences, dailyIntakeRepository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null

        fun getInstance(context: Context, preferences: Preferences): HomeViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeViewModelFactory(
                    Injection.provideUserRepository(context),
                    preferences, Injection.provideDailyIntakeRepository(context)
                )
            }.also { INSTANCE = it }
    }
}