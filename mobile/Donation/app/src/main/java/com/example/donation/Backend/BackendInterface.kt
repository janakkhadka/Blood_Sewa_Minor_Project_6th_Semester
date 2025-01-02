package com.example.donation.backend


import com.example.donation.backend.login.LoginRequest
import com.example.donation.backend.login.LoginResponse
import com.example.donation.backend.registration.PostResponse
import com.example.donation.backend.registration.Registration
import com.example.donation.backend.searchDonor.SearchDonor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface BackendInterface {

   @POST("user/register/")
   suspend fun registerUser(@Body request : Registration): Response<PostResponse>

   @Headers("Content-Type: application/json")
   @POST("user/login/")
   suspend fun loginUser(@Body information: LoginRequest) :Response<LoginResponse>

   @GET("user/blood-group")
   suspend fun searchUser(@Query("blood_group = O-") response : SearchDonor)

}
