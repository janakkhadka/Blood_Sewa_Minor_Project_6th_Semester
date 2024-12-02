package com.example.donation.BottomNavBar

sealed class Route(val route : String){
    object Home : Route("HomeScreen")//home screen for bottomNav bar
    object Profile : Route("ProfileScreen")//profile screen for bottomNav bar
    object Urgent : Route("UrgentBloodScreen")//urgent blood screen for bottomNav bar
}