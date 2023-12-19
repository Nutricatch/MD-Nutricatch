package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.RestaurantRepository
import com.nutricatch.dev.views.navigation.food_recommendation.RestaurantViewModel

class RestaurantViewModelFactory(private val restaurantRepository: RestaurantRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(RestaurantViewModel::class.java) -> {
                return RestaurantViewModel(restaurantRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: RestaurantViewModelFactory? = null

        fun getInstance(context: Context): RestaurantViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: RestaurantViewModelFactory(Injection.provideRestaurantRepository(context))
            }.also { INSTANCE = it }
    }
}