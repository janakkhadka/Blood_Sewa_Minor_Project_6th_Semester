package com.example.donation.DataClasses

data class CreateEvent(
    val name : String,
    val description: String,
    val location : String,
    val collabrator : String,
    val date : String,
    val startTime : String,
    val endTime : String,
    val volunteer_required_count : Int

)
