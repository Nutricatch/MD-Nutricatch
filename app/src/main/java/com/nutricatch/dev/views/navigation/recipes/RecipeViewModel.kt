package com.nutricatch.dev.views.navigation.recipes

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.RecommendationRepository

class RecipeViewModel (private val repository: RecommendationRepository) : ViewModel() {
    val recipes = repository.getRecipes()
}