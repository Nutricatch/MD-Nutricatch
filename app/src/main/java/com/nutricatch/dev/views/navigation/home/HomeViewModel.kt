package com.nutricatch.dev.views.navigation.home

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.RecommendationRepository

class HomeViewModel(private val repository: RecommendationRepository) : ViewModel() {

    val recipes = repository.getRecipes()

    val foods = repository.getFoods()

}