package com.nutricatch.dev.views.navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nutricatch.dev.data.prefs.Preferences
import com.nutricatch.dev.data.repository.RecommendationRepository

class HomeViewModel(private val repository: RecommendationRepository, preferences: Preferences) : ViewModel() {

    val recipes = repository.getRecipes()

    val foods = repository.getFoods()

    val token = preferences.getToken().asLiveData()

}