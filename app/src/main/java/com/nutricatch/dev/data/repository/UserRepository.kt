package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.data.api.response.ActivityLevel
import com.nutricatch.dev.data.api.response.FitnessGoal
import com.nutricatch.dev.data.api.response.Gender
import retrofit2.HttpException

class UserRepository(private val apiService: ApiService) {
    /*
    *  get user profile
    * */

    fun getProfile() = liveData {
        emit(ResultState.Loading)

        try {
            val userResponse = apiService.getProfile()
            emit(ResultState.Success(userResponse))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    /// Nantinya user didirect ke login
                    emit(ResultState.Error("Unauthenticated", e.code()))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun getUserHealth() = liveData {
        emit(ResultState.Loading)

        try {
            val userHeathData = apiService.getHealthData()
            emit(ResultState.Success(userHeathData))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    /// Nantinya user didirect ke login
                    emit(ResultState.Error("Unauthenticated", e.code()))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun updateHealthProfile(
        weight: Double,
        height: Double,
        age: Double,
        gender: String,
        fitnessGoal: String,
        activityLevel: String
    ) = liveData {
        emit(ResultState.Loading)

        try {
            val updateResponse =
                apiService.updateHealthData(
                    weight,
                    height,
                    age,
                    gender,
                    fitnessGoal,
                    activityLevel
                )

            emit(ResultState.Success(updateResponse))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    /// Nantinya user didirect ke login
                    emit(ResultState.Error("Unauthenticated", e.code()))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun getDiamonds() = liveData {
        emit(ResultState.Loading)

        try {
            val diamonds = apiService.getDiamonds()
            emit(ResultState.Success(diamonds))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    /// Nantinya user didirect ke login
                    emit(ResultState.Error("Unauthenticated", e.code()))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun useDiamond() = liveData {
        emit(ResultState.Loading)

        try {
            /*
            * call to use one diamond
            * */
            apiService.useDiamond()
            emit(ResultState.Success(true))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    /// Nantinya user didirect ke login
                    emit(ResultState.Error("Unauthenticated", e.code()))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun getRecommendedNutrition() = liveData {
        emit(ResultState.Loading)

        try {
            val userResponse = apiService.getRecommendedNutrition()
            emit(ResultState.Success(userResponse))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    /// Nantinya user didirect ke login
                    emit(ResultState.Error("Unauthenticated", e.code()))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService)
            }.also { INSTANCE = it }
    }
}