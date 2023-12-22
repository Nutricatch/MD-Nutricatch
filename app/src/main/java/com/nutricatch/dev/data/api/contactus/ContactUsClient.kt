package com.nutricatch.dev.data.api.contactus

import androidx.datastore.preferences.protobuf.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ContactUsClient {
    private const val BASE_URL ="https://script.google.com/a/macros/bangkit.academy/s/AKfycbw9Ai2afXGAGWw4Oai2UyZEmVQwRvP1_ER2pPxwsKkX72V9tqfFhlGOb93Vnx5HjuZRQA/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retrofit.create(ContactUsAPIService::class.java)
}