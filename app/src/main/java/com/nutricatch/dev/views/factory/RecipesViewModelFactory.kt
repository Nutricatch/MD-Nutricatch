package com.nutricatch.dev.views.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nutricatch.dev.data.injection.Injection
import com.nutricatch.dev.data.repository.RecipesRepository
import com.nutricatch.dev.views.navigation.recipes.RecipesViewModel

class RecipesViewModelFactory(private val repository: RecipesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: RecipesViewModelFactory? = null

        fun getInstance(context: Context): RecipesViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RecipesViewModelFactory(Injection.provideRecipeRepository(context))
            }.also { INSTANCE = it }
    }
}