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
    object RequestSection : Screens(route = "RequestSection")
    object SearchDonors : Screens(route = "SearchDonors")
    object MyEvents : Screens(route = "ViewEvents")
    object createEvents : Screens(route = "create events")
    object schedultTime : Screens(route = "Schedule time")
    object eventViewExtended : Screens(route = "EventViewExtended")
    object myBookings : Screens(route = "MyBookings")
    object updateProfile : Screens(route = "Update_Profile")
    object anynomous : Screens(route = "Anynomous_user")
}