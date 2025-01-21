package com.example.donation.backend.registration


data class Registration(
    val name: String,
    val email: String,
    val password: String,
    val district: String,
    val province: String,
    val phone_number: String,
    val gender: String,
    val blood_group: String,
    val DOB: String
)
