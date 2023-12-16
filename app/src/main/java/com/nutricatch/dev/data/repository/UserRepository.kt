package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.data.api.response.ActivityLevel
import com.nutricatch.dev.data.api.response.FitnessGoal
import com.nutricatch.dev.data.api.response.Gender
import retrofit2.HttpException

class UserRepository private constructor(private val apiService: ApiService) {
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
        gender: Gender,
        fitnessGoal: FitnessGoal,
        activityLevel: ActivityLevel
    ) = liveData {
        emit(ResultState.Loading)

        try {
            val updateResponse =
                apiService.updateHealthData(
                    weight,
                    height,
                    age,
                    gender.name,
                    fitnessGoal.name,
                    activityLevel.name
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

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService)
            }.also { INSTANCE = it }
    }
}