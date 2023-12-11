package com.nutricatch.dev.model

data class RecipeListResponse(
    val error: Boolean?,
    val message: String?,
    val recipes: List<Recipe>,
)

data class Recipe(
    val id: Int,
    val title: String?,
    val estimation: String?,
    val imgUrl: String?,
    val caloric: String?,
    val ingredients: List<Ingredient?>,
)

data class Ingredient(
    val id: Int?,
    val name: String?,
    val weight: String?
)