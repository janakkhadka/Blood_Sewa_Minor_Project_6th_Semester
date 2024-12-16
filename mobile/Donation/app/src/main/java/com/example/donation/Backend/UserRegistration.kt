package com.example.donation.Backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRegistration {
    private const val url = "http://127.0.0.1:8000"

    val authService : BackendInterface by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendInterface::class.java)
    }
}