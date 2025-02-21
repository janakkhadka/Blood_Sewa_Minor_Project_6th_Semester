package com.example.donation.DataClasses

import com.google.gson.annotations.SerializedName

data class MyDonationInformation(
    @SerializedName("last_donation_event") val last_donation_event: String,
    @SerializedName("last_donation_date") val last_donation_date: String,
    @SerializedName("donation_count") val donation_count: Int,
    @SerializedName("eligibility_date") val eligibility_date: String,
    @SerializedName("days_left") val days_left: Int
)
