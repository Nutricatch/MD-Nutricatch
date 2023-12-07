package com.nutricatch.dev.model

data class RecipeResponse(
    val error: Boolean?,
    val message: String?,
    val recipes: List<Recipe>,
)

data class Recipe(
    val id: Int,
    val title: String?,
    val estimation: String?,
    val imgUrl: String?,
)
