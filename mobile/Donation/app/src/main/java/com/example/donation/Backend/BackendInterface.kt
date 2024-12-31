package com.example.donation.backend


import com.example.donation.backend.login.LoginRequest
import com.example.donation.backend.login.LoginResponse
import com.example.donation.backend.registration.PostResponse
import com.example.donation.backend.registration.Registration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendInterface {
   //@Headers("Content-Type: application/json")
   @POST("user/register/")
   suspend fun registerUser(@Body request : Registration): Response<PostResponse>

   @POST("user/login/")
   suspend fun loginUser(@Body information: LoginRequest) :Response<LoginResponse>

}
