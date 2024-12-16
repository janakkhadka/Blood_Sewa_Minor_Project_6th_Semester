package com.example.donation.Backend

data class Registration(
    val email : String,
    val username : String,
    val phoneNumber : String,
    val bloodOptions: String,
    val district: String,
    val province: String
)