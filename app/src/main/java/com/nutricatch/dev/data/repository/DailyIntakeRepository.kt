package com.nutricatch.dev.data.repository

import androidx.lifecycle.liveData
import com.nutricatch.dev.data.ResultState
import com.nutricatch.dev.data.api.ApiService
import com.nutricatch.dev.data.api.response.DailyIntakeResponse
import com.nutricatch.dev.data.prefs.Preferences
import retrofit2.HttpException
import java.net.UnknownHostException

class DailyIntakeRepository internal constructor(
    private val apiService: ApiService
){
    fun getDailyIntake() =  liveData {
        emit(ResultState.Loading)
        try {
            val dailyIntakeResponse = DailyIntakeResponse(
                calories = apiService.getDailyIntake().calories,
                protein = apiService.getDailyIntake().protein,
                fats = apiService.getDailyIntake().fats,
                carbohydrates = apiService.getDailyIntake().carbohydrates,
                minFiber = apiService.getDailyIntake().carbohydrates,
                maxSodium = apiService.getDailyIntake().maxSodium,
                maxSugar = apiService.getDailyIntake().maxSugar
            )
            emit(ResultState.Success(dailyIntakeResponse))
        } catch (e: HttpException){
            val errorBody = e.response()?.message()
            when(e.code()){
                in 300..399 ->{
                    emit(ResultState.Error("Need To Reconfigure. Please Contact Administrator"))
                }
                in 400..499 -> {
                    emit(ResultState.Error("Request Error. code ${e.code()} $errorBody"))
                }
                in 500..599 -> {
                    emit(ResultState.Error("Server Error"))
                }
            }
        }catch (e: UnknownHostException) {
            emit(ResultState.Error("Error... Please check your connection"))
        } catch (e: Exception) {
            emit(ResultState.Error("Unknown Error"))
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: DailyIntakeRepository? = null
        fun getInstance(apiService: ApiService) = INSTANCE?: synchronized(this){
            INSTANCE ?: DailyIntakeRepository(apiService)
        }.also { INSTANCE = it }
    }
}