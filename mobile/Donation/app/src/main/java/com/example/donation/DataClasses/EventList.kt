package com.example.donation.DataClasses

data class EventList(
    val name: String,
    val description: String,
    val location: String,
    val date: String,
    val organizer: String,
    val qr_code: String,
    val slug: String,
    val collabrator_name: String
)
