package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.data.prefs.Preferences
import retrofit2.HttpException

class AuthRepository private constructor(
    private val userPreferences: Preferences,
    private val apiService: ApiService
) {
    //Auth Method
    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val success = apiService.loginUser(email, password)
            emit(ResultState.Success(success))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    emit(ResultState.Error("Wrong combination of email and password"))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val success = apiService.registerUser(name, email, password)
            emit(ResultState.Success(success))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> {
                    emit(ResultState.Error("Wrong combination of email and password"))
                }

                else -> {
                    emit(ResultState.Error("Something error. Please contact support"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    suspend fun saveSession(token: String) {
        userPreferences.saveSession(token)
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null
        fun getInstance(preferences: Preferences, apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(preferences, apiService)
            }.also { INSTANCE = it }
    }
}