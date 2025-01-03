package com.example.donation.Navigation

sealed class Screens(val route : String) {
    object Signup : Screens(route = "Screens")
    object Login : Screens(route = "Login")
    object OnBoarding : Screens(route = "OnBoarding")
    object ChangePassword : Screens(route = "ChangePassword")
    object OtpVerification : Screens(route = "OtpVerification")
    object ForgetPassword : Screens(route = "ForgetPassword")
    object BottomNavBar : Screens(route = "BottomNavBar")
    object CustomTopBar : Screens(route = "CustomTopBar")
    object BloodBanks : Screens(route = " BloodBanks")
    object Events : Screens(route = "Events")
    object RequestSection : Screens(route = "RequestSection")
    object SearchDonors : Screens(route = "SearchDonors")
    object ShowCamera : Screens(route = "Showcamera")
}