package com.nutricatch.dev.views.navigation.recipes

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.RecipesRepository

class RecipesViewModel (private val repository: RecipesRepository) : ViewModel() {
    val recipes = repository.getRecipes()
}