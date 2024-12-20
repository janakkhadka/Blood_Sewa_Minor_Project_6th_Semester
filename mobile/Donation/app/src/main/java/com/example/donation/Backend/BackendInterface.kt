package com.example.donation.backend

import com.example.donation.Backend.Login.LoginRequest
import com.example.donation.Backend.Login.LoginResponse
import com.example.donation.Backend.Registration.PostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendInterface {
   //@Headers("Content-Type: application/json")
   @POST("user/register/")
   suspend fun registerUser(@Body request: Registration): Response<PostResponse>

   @POST("user/login/")
   suspend fun loginUser(@Body information: LoginRequest) :Response<LoginResponse>

}
