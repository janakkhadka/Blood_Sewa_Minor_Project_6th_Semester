package com.example.donation.backend.login

data class LoginResponse(
    val message : String,
    val access_token : String,
    val user_Details : Details,
    val refresh_token : String

)