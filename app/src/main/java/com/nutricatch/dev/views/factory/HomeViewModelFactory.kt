package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.RecommendationRepository
import com.nutricatch.dev.views.navigation.home.HomeViewModel
import com.nutricatch.dev.views.navigation.recipes.RecipeViewModel

class HomeViewModelFactory(private val repository: RecommendationRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null

        fun getInstance(context: Context): HomeViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeViewModelFactory(Injection.provideRecommendationRepository(context))
            }.also { INSTANCE = it }
    }
}