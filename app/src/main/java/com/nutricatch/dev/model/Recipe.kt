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
    /// TODO default ini nanti dihapus/diganti pake placeholder
    val imgUrl: String? = "https://spoonacular.com/recipeImages/652965-312x231.jpg",
    val caloric: String?,
    val ingredients: List<Ingredient?>,
)

data class Ingredient(
    val id: Int?,
    val name: String?,
    val weight: String?
)