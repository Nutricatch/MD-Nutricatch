package com.nutricatch.dev.data.api

import com.nutricatch.dev.model.LatestPostResponse
import retrofit2.http.GET

interface ApiService {
    @GET("latest_post")
suspend fun getLatestPost() : LatestPostResponse
}