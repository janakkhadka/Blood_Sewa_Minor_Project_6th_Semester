package com.example.donation.ViewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.donation.DataClasses.BloodRequest
import com.example.donation.DataClasses.CreateEvent
import com.example.donation.DataClasses.EventList
import com.example.donation.DataClasses.OrganizationInventory
import com.example.donation.DataClasses.ScheduleTime
import com.example.donation.backend.UserRegistration
import kotlinx.coroutines.launch


sealed class ResponseState {
    object Loading : ResponseState()
    data class Success(val data: String) : ResponseState()
    data class Error(val message: String) : ResponseState()
}


class SharedViewModel : ViewModel(){


    //scheduling time ko lagi
    private val _responseMessage = MutableStateFlow<String>("")
    val responseMessage: StateFlow<String> = _responseMessage

    private val _responseState = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val responseState: StateFlow<ResponseState> = _responseState

    //blood inventory ko lagi
    private val _inventory = MutableStateFlow<List<OrganizationInventory>>(emptyList())
    val inventory : StateFlow<List<OrganizationInventory>> = _inventory

    //event ko list haru herna laii
    private val _eventList = MutableStateFlow<List<EventList>>(emptyList())
    val eventList : StateFlow<List<EventList>> = _eventList


    private val bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ2MTgwNDgwLCJpYXQiOjE3Mzc1NDA0ODAsImp0aSI6Ijc4ODVkYzU4ZWM4ZjQ2NzZiNTBhNzVmZDA0MmFiZTViIiwidXNlcl9pZCI6MX0.EE4WysRQQisqCiCIZO2Aplr-VfWInThLEHcW02FBDSM"


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
         location : String
    ) {
        viewModelScope.launch {
            try {
                val requestBlood = BloodRequest(
                    patient_name = patient_name,
                    contact = contact,
                    blood_group = blood_group,
                    location = location
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
        date : String
    ){
        viewModelScope.launch {
            try{
                val createData = CreateEvent(
                    name =  name,
                    description = description,
                    location = location,
                    collabrator = collaborator,
                    date = date
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



}
