package com.example.donation.backend

data class Registration(
    val email: String,
    val name: String,
    val phone_number: String,
    val blood_group: String,
    val district: String,
    val province: String,
    val password: String
)
