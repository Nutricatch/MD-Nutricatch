package com.nutricatch.dev.views.navigation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nutricatch.dev.data.api.contactus.ContactUsClient
import com.nutricatch.dev.data.response.ContactUsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactUsViewModel :ViewModel(){
    val status = MutableLiveData<String>()

    fun postMessage(name:String, email:String, message:String)
    {
        ContactUsClient.instance.postData(name, email, message)
            .enqueue(object : Callback<ContactUsResponse> {
                override fun onResponse(
                    call: Call<ContactUsResponse>,
                    response: Response<ContactUsResponse>
                ) {
                    if (response.isSuccessful){
                        status.postValue(response.body()?.status)
                    }
                }

                override fun onFailure(call: Call<ContactUsResponse>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

            })
    }

    fun getStatus(): LiveData<String>
    {
        return status
    }
}