package com.nutricatch.dev.views.navigation.food_detail

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.FoodsRepository

class FoodNutrientViewModel(private val repository: FoodsRepository) : ViewModel() {
    fun getFoodNutrient(name: String) = repository.getFoodByName(name)
}