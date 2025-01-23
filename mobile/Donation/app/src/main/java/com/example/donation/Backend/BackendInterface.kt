package com.example.donation.backend


import com.example.donation.DataClasses.BloodRequest
import com.example.donation.DataClasses.CreateEvent
import com.example.donation.DataClasses.EventList
import com.example.donation.DataClasses.OrganizationInventory
import com.example.donation.DataClasses.ScheduleTime
import com.example.donation.DataClasses.SeeBloodRequest
import com.example.donation.backend.login.LoginRequest
import com.example.donation.backend.login.LoginResponse
import com.example.donation.backend.registration.PostResponse
import com.example.donation.backend.registration.Registration
import com.example.donation.backend.searchDonor.SearchDonor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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
   suspend fun searchUser(
      @Header("Authorization") authorization: String
   ):List<SearchDonor>

   @GET("blood-requests/")
   suspend fun seeBloodRequest(
      @Header("Authorization") authorization: String
   ):List<SeeBloodRequest>

   //scheduling time ko lagi
   @POST("make-bookings/")
   suspend fun createScheduleTime(
      @Header("Authorization") authorization: String,
      @Body scheduleTime: ScheduleTime
   ): Response<Unit>


   //blood request garna ko lagi
   @POST("create/blood-request/")
   suspend fun createBloodRequest(
      @Header("Authorization") authorization: String,
      @Body bloodRequest: BloodRequest
   ): Response<Unit>

   @POST("user/event/create/")
   suspend fun createEvent(
      @Header("Authorization") authorization: String,
      @Body event: CreateEvent
   ): Response<Unit>

   //blood inventory ko lagi
   @GET("blood-inventory/")
   suspend fun getOrganizationInventory(
      @Header("Authorization") authorization: String
   ): List<OrganizationInventory>

   //evenst view garna laii
   @GET("events/")
   suspend fun getEventList(
      @Header("Authorization") authorization: String
   ): List<EventList>


}
