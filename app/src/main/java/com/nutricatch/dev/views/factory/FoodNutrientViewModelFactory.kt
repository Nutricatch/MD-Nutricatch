package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.FoodsRepository
import com.nutricatch.dev.views.navigation.food_detail.FoodNutrientViewModel

class FoodNutrientViewModelFactory(private val repository: FoodsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(FoodNutrientViewModel::class.java) -> {
                return FoodNutrientViewModel(repository) as T
            }
        }
        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: FoodNutrientViewModelFactory? = null

        fun getInstance(context: Context): FoodNutrientViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: FoodNutrientViewModelFactory(Injection.provideFoodsRepository(context))
            }.also { INSTANCE = it }
    }
}