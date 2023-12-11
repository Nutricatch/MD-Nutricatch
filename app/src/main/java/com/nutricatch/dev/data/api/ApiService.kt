package com.nutricatch.dev.data.api

import com.nutricatch.dev.data.response.AuthResponse
import com.nutricatch.dev.model.LatestPostResponse
import com.nutricatch.dev.model.Recipe
import com.nutricatch.dev.model.RecipeResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("latest_post")
    suspend fun getLatestPosts(): LatestPostResponse

    @GET("recipes")
    suspend fun getRecipes(): RecipeResponse

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(@Field("email")email: String, @Field("password")password: String): AuthResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(@Field("name")name: String, @Field("email")email: String, @Field("password")password: String): AuthResponse


}