package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.model.RecipeListResponse
import retrofit2.HttpException
import java.net.UnknownHostException

class RecipesRepository private constructor(private val apiService: ApiService) {
    fun getRecipes() = liveData {
        emit(ResultState.Loading)

        try {
            /// TODO ambil dari api jika sudah ada
            val recipesResponse = RecipeListResponse(
                error = false,
                message = "Recipes retrieved successfully",
                recipes = DummyData.recipesIndonesia
            )

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