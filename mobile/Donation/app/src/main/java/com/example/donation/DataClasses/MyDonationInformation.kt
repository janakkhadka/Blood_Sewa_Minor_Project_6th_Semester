package com.example.donation.DataClasses

data class MyDonationInformation(
    val last_donation_event : String,
    val last_donation_date : String,
    val eligibility_date : String,
    val donation_count : Int,
    val days_left: String

)
