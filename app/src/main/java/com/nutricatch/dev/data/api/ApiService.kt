package com.nutricatch.dev.data.api

import com.nutricatch.dev.model.LatestPostResponse
import com.nutricatch.dev.model.Recipe
import com.nutricatch.dev.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("latest_post")
    suspend fun getLatestPosts(): LatestPostResponse

    @GET("recipes")
    suspend fun getRecipes(): RecipeResponse

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Recipe
}