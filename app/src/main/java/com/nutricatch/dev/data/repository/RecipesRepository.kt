package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.model.Recipe
import com.nutricatch.dev.model.RecipeResponse
import retrofit2.HttpException
import java.net.UnknownHostException

class RecipesRepository private constructor(private val apiService: ApiService) {
    fun getRecipes() = liveData {
        emit(ResultState.Loading)

        try {
            /// TODO ambil dari api jika sudah ada
            val recipesResponse = RecipeResponse(
                error = false,
                message = "Recipes retrieved successfully",
                recipes = listOf(
                    Recipe(
                        id = 1,
                        title = "Pizza Margherita",
                        estimation = "25 minutes",
                        imgUrl = "https://example.com/images/pizza-margherita.jpg"
                    ),
                    Recipe(
                        id = 2,
                        title = "Beef Tacos",
                        estimation = "30 minutes",
                        imgUrl = "https://example.com/images/beef-tacos.jpg"
                    ),
                    Recipe(
                        id = 3,
                        title = "Chicken Tikka Masala",
                        estimation = "45 minutes",
                        imgUrl = "https://example.com/images/chicken-tikka-masala.jpg"
                    ),
                    Recipe(
                        id = 4,
                        title = "Pad Thai",
                        estimation = "20 minutes",
                        imgUrl = "https://example.com/images/pad-thai.jpg"
                    ),
                    Recipe(
                        id = 5,
                        title = "Sushi Rolls",
                        estimation = "60 minutes",
                        imgUrl = "https://example.com/images/sushi-rolls.jpg"
                    ),
                    Recipe(
                        id = 6,
                        title = "Vegetarian Lasagna",
                        estimation = "45 minutes",
                        imgUrl = "https://example.com/images/vegetarian-lasagna.jpg"
                    ),
                    Recipe(
                        id = 7,
                        title = "French Toast",
                        estimation = "20 minutes",
                        imgUrl = "https://example.com/images/french-toast.jpg"
                    ),
                    Recipe(
                        id = 8,
                        title = "Pancakes",
                        estimation = "30 minutes",
                        imgUrl = "https://example.com/images/pancakes.jpg"
                    ),
                    Recipe(
                        id = 9,
                        title = "Smoothie Bowl",
                        estimation = "15 minutes",
                        imgUrl = "https://example.com/images/smoothie-bowl.jpg"
                    ),
                    Recipe(
                        id = 10,
                        title = "Chicken Caesar Salad",
                        estimation = "30 minutes",
                        imgUrl = "https://example.com/images/chicken-caesar-salad.jpg"
                    )
                )
            )
//            val postResponse = apiService.getLatestPost()
            emit(ResultState.Success(recipesResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.message()
            when (val code = e.code()) {
                in 300..399 -> {
                    emit(ResultState.Error("Need To Reconfigure. Please Contact Administrator"))
                }

                in 400..499 -> {
                    emit(ResultState.Error("Request Error. code $code $errorBody"))
                }

                in 500..599 -> {
                    emit(ResultState.Error("Server Error"))
                }
            }
        } catch (e: UnknownHostException) {
            emit(ResultState.Error("Error... Please check your connection"))
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RecipesRepository? = null
        fun getInstance(apiService: ApiService) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RecipesRepository(apiService)
        }.also { INSTANCE = it }
    }
}