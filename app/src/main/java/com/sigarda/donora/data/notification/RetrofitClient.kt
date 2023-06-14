package com.sigarda.donora.data.notification

import com.sigarda.donora.data.network.service.FCMInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://fcm.googleapis.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: FCMInterface by lazy {
        retrofit.create(FCMInterface::class.java)
    }
}