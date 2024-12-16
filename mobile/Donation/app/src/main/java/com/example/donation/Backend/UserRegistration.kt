package com.example.donation.Backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRegistration {
    private const val url = "http://172.16.7.121:8000/api/"

    val authService : BackendInterface by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendInterface::class.java)
    }
}