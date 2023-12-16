package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
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

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(apiService: ApiService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService)
            }.also { INSTANCE = it }
    }
}