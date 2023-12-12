package com.nutricatch.dev.data.repository

import com.nutricatch.dev.model.Food
import com.nutricatch.dev.model.Ingredient
import com.nutricatch.dev.model.Recipe

object DummyData {
    val recipesIndonesia: List<Recipe> = listOf(
        Recipe(
            id = 1,
            title = "Nasi Goreng",
            estimation = "30 menit",
            caloric = "350 kkal",
            ingredients = listOf(
                Ingredient(id = 1, name = "Nasi putih", weight = "1 porsi"),
                Ingredient(id = 2, name = "Telur", weight = "1 butir"),
                Ingredient(id = 3, name = "Bawang merah", weight = "2 siung"),
                Ingredient(id = 4, name = "Bawang putih", weight = "1 siung"),
                Ingredient(id = 5, name = "Cabai merah", weight = "2 buah"),
                Ingredient(id = 6, name = "Saus tiram", weight = "1 sdm"),
                Ingredient(id = 7, name = "Kecap manis", weight = "1 sdm"),
                Ingredient(id = 8, name = "Garam", weight = "secukupnya"),
                Ingredient(id = 9, name = "Merica", weight = "secukupnya"),
            )
        ),
        Recipe(
            id = 2,
            title = "Rendang",
            estimation = "2 jam",
            caloric = "450 kkal",
            ingredients = listOf(
                Ingredient(id = 1, name = "Daging sapi", weight = "500 gr"),
                Ingredient(id = 2, name = "Santan kelapa", weight = "500 ml"),
                Ingredient(id = 3, name = "Bawang merah", weight = "5 siung"),
                Ingredient(id = 4, name = "Bawang putih", weight = "3 siung"),
                Ingredient(id = 5, name = "Cabai merah", weight = "5 buah"),
                Ingredient(id = 6, name = "Jahe", weight = "1 ruas"),
                Ingredient(id = 7, name = "Kunyit", weight = "1 ruas"),
                Ingredient(id = 8, name = "Serai", weight = "2 batang"),
                Ingredient(id = 9, name = "Daun salam", weight = "2 lembar"),
                Ingredient(id = 10, name = "Garam", weight = "secukupnya"),
                Ingredient(id = 11, name = "Gula merah", weight = "secukupnya"),
            )
        ),
        Recipe(
            id = 3,
            title = "Sate Ayam",
            estimation = "45 menit",
            caloric = "300 kkal",
            ingredients = listOf(
                Ingredient(id = 1, name = "Dada ayam", weight = "500 gr"),
                Ingredient(id = 2, name = "Bawang merah", weight = "5 siung"),
                Ingredient(id = 3, name = "Bawang putih", weight = "3 siung"),
                Ingredient(id = 4, name = "Kecap manis", weight = "3 sdm"),
                Ingredient(id = 5, name = "Saus tiram", weight = "1 sdm"),
                Ingredient(id = 6, name = "Minyak goreng", weight = "secukupnya"),
                Ingredient(id = 7, name = "Garam", weight = "secukupnya"),
                Ingredient(id = 8, name = "Merica", weight = "secukupnya"),
                Ingredient(id = 9, name = "Tusuk sate", weight = "secukupnya"),
                Ingredient(id = 10, name = "Bumbu kacang", weight = "secukupnya"),
            )
        ),
    )

    val foods = listOf(
        Food(
            id = 1, name = "Apple", caloric = "52 Kcal", carb = "13.8 g", fat = "0.2 g"
        ),
        Food(
            id = 2, name = "Banana", caloric = "105 Kcal", carb = "27 g", fat = "0.3 g"
        ),
        Food(
            id = 3, name = "Orange", caloric = "47 Kcal", carb = "11.2 g", fat = "0.1 g"
        ),
        Food(
            id = 4, name = "Chicken breast", caloric = "165 Kcal", carb = "0 g", fat = "3.6 g"
        ),
        Food(
            id = 5, name = "Salmon", caloric = "208 Kcal", carb = "0 g", fat = "12.1 g"
        ),
        Food(
            id = 6, name = "Brown rice", caloric = "216 Kcal", carb = "45 g", fat = "1.8 g"
        ),
        Food(
            id = 7, name = "Broccoli", caloric = "34 Kcal", carb = "6 g", fat = "0.3 g"
        ),
    )
}