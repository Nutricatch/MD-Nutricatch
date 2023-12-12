package com.nutricatch.dev.model

data class FoodsResponse(
    val error: Boolean?,
    val message: String?,
    val foods: List<Food>,
)

data class Food(
    val id: Int,
    val name: String?,
    val caloric: String?,
    val carb: String?,
    val fat: String?,
    /// TODO default ini nanti dihapus/diganti pake placeholder
    val imgUrl: String? = "https://spoonacular.com/recipeImages/652965-312x231.jpg",
)
