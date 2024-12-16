package com.example.donation.Backend

import retrofit2.http.Body
import retrofit2.http.POST

interface BackendInterface {
    @POST("/register/")
    suspend fun registerUser(@Body request : Registration) : Response
}