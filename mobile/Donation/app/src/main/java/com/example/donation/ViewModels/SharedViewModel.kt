package com.example.donation.ViewModels

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.donation.DataClasses.MyJoinedEvents
import com.example.donation.DataClasses.MyVolunteeredEvents
import com.example.donation.DataClasses.OrganizationInventory
import com.example.donation.DataClasses.ScheduleTime
import com.example.donation.DataClasses.SeeBloodRequest
import com.example.donation.DataClasses.TodaysEvent
import com.example.donation.DataClasses.UpcomingEvents
import com.example.donation.DataClasses.UpdateInformation
import com.example.donation.backend.UserRegistration
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed class ResponseState {
    object Loading : ResponseState()
    data class Success(val data: String) : ResponseState()
    data class Error(val message: String) : ResponseState()
}


@SuppressLint("StaticFieldLeak")
class SharedViewModel : ViewModel(){
    @SuppressLint("CompositionLocalNaming")
    val context = LocalContext


    //profile update garna ko lagi
    private val _updateProfile = MutableStateFlow<List<UpdateInformation>>(emptyList())
    val updateProfile: StateFlow<List<UpdateInformation>>  = _updateProfile

    //created events ko lagi
    private val _createdEvents = MutableStateFlow<List<MyCreatedEvent>>(emptyList())
    val createdEvents : StateFlow<List<MyCreatedEvent>> = _createdEvents

    //volunteering hostory ko lagi
    private val _historyVolunteered = MutableStateFlow<List<MyVolunteeredEvents>>(emptyList())
    val historyVolunteered: StateFlow<List<MyVolunteeredEvents>> = _historyVolunteered

    //event join garna ko lagi
    private val _joinEventStatus = MutableLiveData<Result<JoinResponse?>>()
    val joinEventStatus: LiveData<Result<JoinResponse?>> = _joinEventStatus

    //volunteering ko lagi
    private val _doVolunteerStatus = MutableLiveData<Result<DoVolunteer?>>()
    val doVolunteerStatus : LiveData<Result<DoVolunteer?>> = _doVolunteerStatus

    //checked in garna ko lagi event
    private val _checkInStatus = MutableLiveData<Result<CheckedInEvent?>>()
    val checkInStatus : LiveData<Result<CheckedInEvent?>> = _checkInStatus

    //checked in garna ko lagi volunteer
    private val _checkInStatusVolunteer = MutableLiveData<Result<CheckedInVolunteer?>>()
    val checkInStatusVolunteer : LiveData<Result<CheckedInVolunteer?>> = _checkInStatusVolunteer


    //scheduling time ko lagi
    private val _responseMessage = MutableStateFlow<String>("")
    val responseMessage: StateFlow<String> = _responseMessage

    private val _responseState = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val responseState: StateFlow<ResponseState> = _responseState

    //blood inventory ko lagi
    private val _inventory = MutableStateFlow<List<OrganizationInventory>>(emptyList())
    val inventory : StateFlow<List<OrganizationInventory>> = _inventory


    //event history ko lagi
    private val _historyListJoined = MutableStateFlow<List<MyJoinedEvents>>(emptyList())
    val historyListJoined: StateFlow<List<MyJoinedEvents>> = _historyListJoined

    //event ko list haru herna laii
    private val _eventList = MutableStateFlow<List<EventList>>(emptyList())
    val eventList : StateFlow<List<EventList>> = _eventList

    //upcoming events ko lgai
    private val _eventUpList = MutableStateFlow<List<UpcomingEvents>>(emptyList())
    val eventUpList : StateFlow<List<UpcomingEvents>> = _eventUpList

    //blood request har herna laii
    private val _bloodRequests = MutableStateFlow<List<SeeBloodRequest>>(emptyList())
    val bloodRequests: StateFlow<List<SeeBloodRequest>> = _bloodRequests

    //todays event ko lagi
    private val _todayEvent = MutableStateFlow<List<TodaysEvent>>(emptyList())
    val todayEvent: StateFlow<List<TodaysEvent>> = _todayEvent

    //my bookings check garna laii
    private val _myBookings = MutableStateFlow<List<MyBookings>>(emptyList())
    val myBookings: StateFlow<List<MyBookings>> = _myBookings

    //organization ko list haru herna laii
    private val _organizations = MutableStateFlow<List<String>>(emptyList())
    val organizations : StateFlow<List<String>> = _organizations

    //eventHistory check grana ko lagi
    private val _history = MutableStateFlow<List<EventDonationHistory>>(emptyList())
    val history: StateFlow<List<EventDonationHistory>> get() = _history

    //blood group anusar ko filter garna ko lagiii
    private val _values = MutableStateFlow<List<BloodGroupSearch>>(emptyList())
    val values : StateFlow<List<BloodGroupSearch>> = _values


    private val bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ2MTgwNDgwLCJpYXQiOjE3Mzc1NDA0ODAsImp0aSI6Ijc4ODVkYzU4ZWM4ZjQ2NzZiNTBhNzVmZDA0MmFiZTViIiwidXNlcl9pZCI6MX0.EE4WysRQQisqCiCIZO2Aplr-VfWInThLEHcW02FBDSM"
   // private val bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ2MzQzNzE4LCJpYXQiOjE3Mzc3MDM3MTgsImp0aSI6ImU1Zjg1MTE5ZGU0NzRiMjk5YWVjMWM2Y2JjMTdiZmI1IiwidXNlcl9pZCI6MX0.iQ6cjbKbzw0S9-Z3BhqkX9-OZOxjU1ETAkck-FPzohA"


    fun createScheduleTime(
        organization: String,
        bookingDate: String,
        shift: String
    ) {
        viewModelScope.launch {
            try {
                val scheduleTime = ScheduleTime(
                    organization = organization,
                    booking_date = bookingDate,
                    shift = shift
                )


                Log.d("ScheduleTimeRequest", "Sending: $scheduleTime")

                val response = UserRegistration.authService.createScheduleTime(
                    "Bearer $bearerToken",
                    scheduleTime
                )

                if (response.isSuccessful) {
                    _responseMessage.value = "Schedule time created successfully!"
                    Log.d("ScheduleTimeResponse", "Success: ${response.body()}")
                } else {
                    _responseMessage.value = "Error: ${response.message()}"
                    Log.e("ScheduleTimeError", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _responseMessage.value = "Error: ${e.message}"
                Log.e("ScheduleTimeError", "Exception: ${e.message}")
            }
        }
    }


    //create blood request
    fun createBloodRequest(
        patient_name : String,
        contact : String,
        blood_group: String,
        province : String,
        district : String,
        city : String
    ) {
        viewModelScope.launch {
            try {
                val requestBlood = BloodRequest(
                    patient_name = patient_name,
                    contact = contact,
                    blood_group = blood_group,
                    province = province,
                    district = district,
                    city = city
                )


                Log.d("ScheduleTimeRequest", "Sending: $requestBlood")

                val response = UserRegistration.authService.createBloodRequest(
                    "Bearer $bearerToken",
                    requestBlood
                )

                if (response.isSuccessful) {
                    _responseMessage.value = "Schedule time created successfully!"
                    Log.d("RequestBlood", "Success: ${response.body()}")
                } else {
                    _responseMessage.value = "Error: ${response.message()}"
                    Log.e("CreateEventError", "Error: ${response.errorBody()?.string()}")
                    _responseState.value = ResponseState.Error("Failed to create event: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _responseMessage.value = "Error: ${e.message}"
                Log.e("RequestBlood", "Exception: ${e.message}")
                _responseState.value = ResponseState.Error("An error occurred: ${e.message}")
                Log.e("CheckValue","${_responseState.value}")
            }
        }
    }

    //create event

    fun createEvent(
        name : String,
        description: String,
        location : String,
        collaborator : String,
        date : String,
        startTime : String,
        endTime : String,
        volunteerReq : Int
    ){
        viewModelScope.launch {
            try{
                val createData = CreateEvent(
                    name =  name,
                    description = description,
                    location = location,
                    collabrator = collaborator,
                    date = date,
                    endTime = endTime,
                    startTime = startTime,
                    volunteer_required_count = volunteerReq
                )
                val response = UserRegistration.authService.createEvent(
                    "Bearer $bearerToken",
                    createData

                )
                if (response.isSuccessful) {
                    _responseMessage.value = "Schedule time created successfully!"
                    Log.d("RequestBlood", "Success: ${response.body()}")
                } else {
                    _responseMessage.value = "Error: ${response.message()}"
                    Log.e("CreateEventError", "Error: ${response.errorBody()?.string()}")
                    _responseState.value = ResponseState.Error("Failed to create event: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _responseMessage.value = "Error: ${e.message}"
                Log.e("RequestBlood", "Exception: ${e.message}")
                _responseState.value = ResponseState.Error("An error occurred: ${e.message}")
                Log.e("CheckValue","${_responseState.value}")

            }
        }
    }
//blood inventory ko lagi
fun fetchOrgData(){
    viewModelScope.launch {
        try {
            val response = UserRegistration.authService.getOrganizationInventory("Bearer $bearerToken")
            _inventory.value = response
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("checkValue","${e.message}")
        }

    }
}

    //event list har herna ko lagi
    fun fetchEventsList(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getEventList("Bearer $bearerToken")
                _eventList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }

        }
    }

    //blood request fetrch garna laii
    fun fetchBloodRequests() {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.seeBloodRequest("Bearer $bearerToken")
                _bloodRequests.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }
        }
    }

    fun fetchOrganizations() {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getOrganizationList("Bearer $bearerToken")


                if (response.organization.isEmpty()) {
                    Log.e("checkValue", "No organizations found in response.")
                    _organizations.value = emptyList()
                } else {
                    Log.d("Response", "Organizations: ${response.organization}")
                    _organizations.value = response.organization
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue", "Error: ${e.message}")
            }
        }
    }

    //fetch donation history

    fun fetchDonationHistory() {
        viewModelScope.launch {
            try {
                Log.d("fetchDonationHistory", "Fetching donation history...")
                val response = UserRegistration.authService.getMyDonationHistory("Bearer $bearerToken")
                Log.d("fetchDonationHistory", "Received response: $response")
                _history.value = response
            } catch (e: HttpException) {
                Log.e("fetchDonationHistory", "HTTP error: ${e.response()?.errorBody()?.string()}")
            } catch (e: IOException) {
                Log.e("fetchDonationHistory", "Network error: ${e.message}")
            } catch (e: Exception) {
                Log.e("fetchDonationHistory", "Unexpected error: ${e.message}", e)
            }
        }
    }

    fun fetchDataBloodGroup() {
        viewModelScope.launch {
            try {
                Log.d("fetchDataBloodGroup", "Fetching blood group data...")
                val response = UserRegistration.authService.getBloodGroupsData("Bearer $bearerToken")
                Log.d("fetchDataBloodGroup", "Received response: $response")
                _values.value = response
            } catch (e: HttpException) {
                Log.e("fetchDataBloodGroup", "HTTP error: ${e.response()?.errorBody()?.string()}")
            } catch (e: IOException) {
                Log.e("fetchDataBloodGroup", "Network error: ${e.message}")
            } catch (e: Exception) {
                Log.e("fetchDataBloodGroup", "Unexpected error: ${e.message}", e)
            }
        }
    }

    //fetch upcoming events
    fun fetchUpcomingEventsList(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getUpcomingEvents("Bearer $bearerToken")
                _eventUpList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }

        }
    }

    //fetch upcoming events
    fun fetchMyBookings(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getMyBookings("Bearer $bearerToken")
                _myBookings.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }

        }
    }

    //fetch upcoming events
    fun fetchTodaysEvent(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getTodaysEvent("Bearer $bearerToken")
                _todayEvent.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }

        }
    }


    fun joinEvent(slug: String) {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.joinEvent(slug,"Bearer $bearerToken")
                if (response.isSuccessful) {
                    _joinEventStatus.value = Result.success(response.body())
                } else {
                    _joinEventStatus.value = Result.failure(Throwable("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _joinEventStatus.value = Result.failure(e)
            }
        }
    }


    fun checkInEvent(slug: String) {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.confirmEvent(slug,"Bearer $bearerToken")
                if (response.isSuccessful) {
                    _checkInStatus.value = Result.success(response.body())
                } else {
                    _checkInStatus.value = Result.failure(Throwable("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _checkInStatus.value = Result.failure(e)
            }
        }
    }


//    volunteering ko lagi
    fun doVolunteering(slug: String) {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.doVolunteering(slug,"Bearer $bearerToken")
                if (response.isSuccessful) {
                    _doVolunteerStatus.value = Result.success(response.body())
                } else {
                    _doVolunteerStatus.value = Result.failure(Throwable("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _doVolunteerStatus.value = Result.failure(e)
            }
        }
    }

    //volunteer checkin
    fun checkInVolunteer(slug: String) {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.confirmVolunteer(slug,"Bearer $bearerToken")
                if (response.isSuccessful) {
                    _checkInStatusVolunteer.value = Result.success(response.body())
                } else {
                    _checkInStatusVolunteer.value = Result.failure(Throwable("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _checkInStatusVolunteer.value = Result.failure(e)
            }
        }
    }

    //fetch my eveny history
    fun fetchEventHistory(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getMyHistory("Bearer $bearerToken")
                _historyListJoined.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }

        }
    }


    //fetch my volunteering hostory
    fun fetchVolunteeringHistory(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getVolunteeredEvents("Bearer $bearerToken")
                _historyVolunteered.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkValue","${e.message}")
            }

        }
    }

    //fetch my created events
    fun fetchCreatedEvents(){
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.getMyCreatedEvents("Bearer $bearerToken")
                _createdEvents.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("checkmessage","${e.message}")
            }

        }
    }

    //update the profile
    fun updateUser(phoneNumber: String, password: String) {
        viewModelScope.launch {
            try {
                val request = UpdateInformation(phone_number = phoneNumber, password = password)
                val response = UserRegistration.authService.updateProfile("Bearer $bearerToken",request)
                if (response.isSuccessful) {
                    _responseMessage.value = "Profile updated successfully!"

                    Log.d("Update", "User updated successfully")
                } else {
                    Log.e("Update", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("Update Error", "Exception: ${e.localizedMessage}")
            }
        }
    }



}
