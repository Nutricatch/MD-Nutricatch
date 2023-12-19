package com.nutricatch.dev.data.api.contactus

import com.nutricatch.dev.data.response.ContactUsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ContactUsAPIService {
    @FormUrlEncoded
    @POST("exec")
    fun postData(
        @Field("nama")name: String,
        @Field("email")email: String,
        @Field("message")message:String): Call<ContactUsResponse>
}