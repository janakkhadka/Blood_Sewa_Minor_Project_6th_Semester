package com.example.donation.backend


import com.example.donation.DataClasses.BloodGroupSearch
import com.example.donation.DataClasses.BloodRequest
import com.example.donation.DataClasses.CheckedInEvent
import com.example.donation.DataClasses.CheckedInVolunteer
import com.example.donation.DataClasses.CreateEvent
import com.example.donation.DataClasses.DoVolunteer
import com.example.donation.DataClasses.EventDonationHistory
import com.example.donation.DataClasses.EventList
import com.example.donation.DataClasses.JoinResponse
import com.example.donation.DataClasses.MyBookings
import com.example.donation.DataClasses.MyCreatedEvent
import com.example.donation.DataClasses.MyDonationInformation
import com.example.donation.DataClasses.MyJoinedEvents
import com.example.donation.DataClasses.MyVolunteeredEvents
import com.example.donation.DataClasses.OrganizationInventory
import com.example.donation.DataClasses.OrganizationList
import com.example.donation.DataClasses.ScheduleTime
import com.example.donation.DataClasses.SeeBloodRequest
import com.example.donation.DataClasses.TodaysEvent
import com.example.donation.DataClasses.UpcomingEvents
import com.example.donation.DataClasses.UpdateInformation
import com.example.donation.DataClasses.updateResponse
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
import retrofit2.http.PUT
import retrofit2.http.Path

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
   @GET("upcomingevents/")
   suspend fun getEventList(
      @Header("Authorization") authorization: String
   ): List<EventList>

   //organization ko list check garna ko lagi
   @GET("organization-list/")
   suspend fun getOrganizationList(
      @Header("Authorization") authorization: String
   ): OrganizationList

   //my donation history
   @GET("my-events-history/")
   suspend fun getMyDonationHistory(
      @Header("Authorization") authorization: String
   ): List<EventDonationHistory>

   @GET("user/blood-group/")
   suspend fun getBloodGroupsData(
      @Header("Authorization") authorization: String
   ): List<BloodGroupSearch>

   //upcomming events ko lagi
   @GET("upcomingevents/")
   suspend fun getUpcomingEvents(
      @Header("Authorization") authorization: String
   ): List<UpcomingEvents>

   //myBookings ko lagi
   @GET("my-user-bookings/")
   suspend fun getMyBookings(
      @Header("Authorization") authorization: String
   ): List<MyBookings>

   @GET("todayevents/")
   suspend fun getTodaysEvent(
      @Header("Authorization") authorization: String
   ): List<TodaysEvent>

   //event join garna ko lagi
   @POST("events/{slug}/join/")
   suspend fun joinEvent(
      @Path("slug") slug: String,
      @Header("Authorization") token: String
   ): Response<JoinResponse>

   @POST("events/{slug}/checkin/")
   suspend fun confirmEvent(
      @Path("slug") slug: String,
      @Header("Authorization") token: String
   ) : Response<CheckedInEvent>

   //event join garna ko lagi
   @POST("events/{slug}/volunteer/")
   suspend fun doVolunteering(
      @Path("slug") slug: String,
      @Header("Authorization") token: String
   ): Response<DoVolunteer>

   //volunteer checkin
   @POST("events/{slug}/confirm-volunteer/")
   suspend fun confirmVolunteer(
      @Path("slug") slug: String,
      @Header("Authorization") token: String
   ) : Response<CheckedInVolunteer>


   //my-event history
   @GET("my-events-history/")
   suspend fun getMyHistory(
      @Header("Authorization") authorization: String
   ): List<MyJoinedEvents>

   @GET("my-volunteering-history/")
   suspend fun getVolunteeredEvents(
      @Header("Authorization") authorization: String
   ): List<MyVolunteeredEvents>

   //my created events  ko lagi
   @GET("my-all-events/")
   suspend fun getMyCreatedEvents(
      @Header("Authorization") authorization: String
   ): List<MyCreatedEvent>

   @PUT("user/profile/update/")
   suspend fun updateProfile(
      @Header("Authorization") authorization: String,
      @Body request: UpdateInformation
   ) : Response<Unit>

   //my donation information
   @GET("my-donation-info/")
   suspend fun getMyDonationInfo(
      @Header("Authorization") authorization: String
   ):MyDonationInformation

   @POST("public/blood-request/")
   suspend fun createAnonymousRequest(
      @Body bloodRequest: BloodRequest
   ): Response<Unit>








}
