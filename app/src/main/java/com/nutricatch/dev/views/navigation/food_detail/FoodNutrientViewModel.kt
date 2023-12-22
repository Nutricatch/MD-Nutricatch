package com.nutricatch.dev.views.navigation.food_detail

import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.repository.FoodsRepository
import com.nutricatch.dev.data.repository.UserRepository

class FoodNutrientViewModel(
    private val foodsRepository: FoodsRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun getFoodNutrient(name: String) = foodsRepository.getFoodByName(name)

    fun saveEating(
        foodName: String,
        calories: Double,
        carbohydrates: Double,
        fat: Double,
        protein: Double,
        salt: Double,
        sugar: Double,
        fiber: Double
    ) = foodsRepository.addNewEating(
        foodName,
        calories,
        carbohydrates,
        fat,
        protein,
        salt,
        sugar,
        fiber
    )

    fun useDiamond() = userRepository.useDiamond()
}