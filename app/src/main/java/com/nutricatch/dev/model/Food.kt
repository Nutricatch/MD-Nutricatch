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
    val imgUrl: String?,
)
