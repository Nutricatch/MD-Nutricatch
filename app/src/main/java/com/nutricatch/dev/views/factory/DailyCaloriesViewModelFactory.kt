package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.DailyIntakeRepository
import com.nutricatch.dev.views.navigation.daily_calories.DailyCaloriesViewModel


class DailyCaloriesViewModelFactory(private val repository: DailyIntakeRepository) :ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DailyCaloriesViewModel::class.java) -> {
                return DailyCaloriesViewModel(repository) as T
            }
        }
        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: DailyCaloriesViewModelFactory? = null

        fun getInstance(context: Context): DailyCaloriesViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyCaloriesViewModelFactory(Injection.provideDailyIntakeRepository(context))
            }.also { INSTANCE = it }
    }
}