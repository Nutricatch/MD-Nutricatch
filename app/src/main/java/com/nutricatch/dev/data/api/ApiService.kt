package com.nutricatch.dev.data.api

import com.nutricatch.dev.data.api.response.DailyIntakeResponse
import com.nutricatch.dev.data.api.response.HealthResponse
import com.nutricatch.dev.data.api.response.RecommendedNutritionResponse
import com.nutricatch.dev.data.api.response.RestaurantResponseItem
import com.nutricatch.dev.data.api.response.UserResponse
import com.nutricatch.dev.data.response.AuthResponse
import com.nutricatch.dev.model.LatestPostResponse
import com.nutricatch.dev.model.Recipe
import com.nutricatch.dev.model.RecipeListResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("latest_post")
    suspend fun getLatestPosts(): LatestPostResponse

    @GET("recipes")
    suspend fun getRecipes(): RecipeListResponse

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe


    /*
    *   Auth Section
    * */
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    /*
    *   User Profile and health
    * */
    @GET("user-health/profile")
    suspend fun getProfile(): UserResponse

    @GET("user-health/health")
    suspend fun getHealthData(): HealthResponse

    @FormUrlEncoded
    @POST("/user-health/update")
    suspend fun updateHealthData(
        @Field("weight") weight: Double,
        @Field("height") height: Double,
        @Field("age") age: Double,
        @Field("gender") gender: String,
        @Field("fitnessGoal") fitnessGoal: String,
        @Field("activityLevel") activityLevel: String,
    ): HealthResponse

    @GET("/restaurants/search")
    suspend fun getNearbyRestaurants(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double
    ): MutableList<RestaurantResponseItem>


    /*
    *   User daily food intake
    * */
    //TODO Buat fungsi API Service untuk get daily Calories dan Post new food
    @GET("/daily-consumtion/all-daily-consumtion")
    suspend fun getDailyIntake(): DailyIntakeResponse

    @FormUrlEncoded
    @POST()
    suspend fun insertNewFood(@Part foodImage: MultipartBody.Part): DailyIntakeResponse

    @GET("nutrition-recommender/daily-recomended-nutrition")
    suspend fun getRecommendedNutrition(): RecommendedNutritionResponse
}