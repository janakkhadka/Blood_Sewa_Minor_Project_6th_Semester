package com.example.donation.ViewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.example.donation.DataClasses.ScheduleTime
import com.example.donation.backend.UserRegistration
import kotlinx.coroutines.launch


//@HiltViewModel
class SharedViewModel : ViewModel(){


    //scheduling time ko lagi
    private val _responseMessage = MutableStateFlow<String>("")
    val responseMessage: StateFlow<String> = _responseMessage

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



}
