package com.example.donation.backend.searchDonor

data class SearchDonor(
    val name : String,
    val phone_number : String,
    val blood_group : String,
    val district: String,
    val province : String,
    val age : String
)
