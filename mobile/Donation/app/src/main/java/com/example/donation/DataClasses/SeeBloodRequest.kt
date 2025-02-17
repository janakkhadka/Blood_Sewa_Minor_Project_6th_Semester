package com.example.donation.DataClasses

data class SeeBloodRequest(
    val user_name : String,
    val patient_name : String,
    val contact : String,
    val blood_group : String,
    val province : String,
    val district : String,
    val city : String,
    val date : String
)
