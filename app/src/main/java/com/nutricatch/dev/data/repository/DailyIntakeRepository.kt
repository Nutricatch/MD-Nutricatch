package com.nutricatch.dev.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.utils.todayDate
import retrofit2.HttpException
import java.net.UnknownHostException

class DailyIntakeRepository internal constructor(
    private val apiService: ApiService
) {
    fun getDailyIntake(date: String = todayDate) = liveData {
        emit(ResultState.Loading)
        try {
            val dailyIntakeResponse = apiService.getConsumptionByDate(date)
            emit(ResultState.Success(dailyIntakeResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.message()
            when (e.code()) {
                in 300..399 -> {
                    emit(ResultState.Error("Need To Reconfigure. Please Contact Administrator"))
                }

                in 400..499 -> {
                    emit(ResultState.Error("Request Error. code ${e.code()} $errorBody"))
                }

                in 500..599 -> {
                    emit(ResultState.Error("Server Error"))
                }
            }
        } catch (e: UnknownHostException) {
            emit(ResultState.Error("Error... Please check your connection"))
        } catch (e: Exception) {
            Log.e("TAG", "getDailyIntake: ${e.message}")
            emit(ResultState.Error("Unknown Error "))
        }
    }

    fun getTodayConsumption() = liveData {
        emit(ResultState.Loading)

        try {
            val consumeResponses = apiService.getConsumptionByDate(todayDate)
            Log.d("TAG", "getTodayConsumption: $todayDate")
            emit(ResultState.Success(consumeResponses))
        } catch (e: HttpException) {
            val errorBody = e.response()?.message()
            when (e.code()) {
                in 300..399 -> {
                    emit(ResultState.Error("Need To Reconfigure. Please Contact Administrator"))
                }

                in 400..499 -> {
                    emit(ResultState.Error("Request Error"))
                }

                in 500..599 -> {
                    emit(ResultState.Error("Server Error"))
                }
            }
        } catch (e: UnknownHostException) {
            emit(ResultState.Error("Error... Please check your connection"))
        } catch (e: Exception) {
            Log.e("TAG", "getDailyIntake: ${e.message}")
            emit(ResultState.Error("Unknown Error "))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DailyIntakeRepository? = null
        fun getInstance(apiService: ApiService) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DailyIntakeRepository(apiService)
        }.also { INSTANCE = it }
    }
}