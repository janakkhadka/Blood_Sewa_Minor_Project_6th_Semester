package com.example.donation.DataClasses


data class MyCreatedEvent(
    val name : String,
    val location : String,
    val date : String,
    val organizer : String,
    val qr_code : String,
    val slug : String,
    val collabrator_name : String,
    val donot_attendee_count : Int,
    val start_time : String,
    val end_time : String,
    val expected_donot_count: String,
    val volunteer_required_count : Int,
    val volunteer_attendee_count: Int
)
