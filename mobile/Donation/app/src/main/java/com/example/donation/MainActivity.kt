package com.example.donation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.HomeScreen
import com.example.donation.BottomNavBar.ProfileScreen
import com.example.donation.ExtraItems.EventViewExtended
import com.example.donation.ExtraItems.MyBookings
import com.example.donation.ExtraItems.MyEvents
import com.example.donation.ExtraItems.SearchDonors
import com.example.donation.Navigation.SetUpNavigation


class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            //SetUpNavigation(navController)
            //ViewEvents(navController)
            //BloodBanks(navController)
            //SearchDonors(navController)
            //CreateEvents()
          //  Events(navController)
           // ScheduleTime(navController)
           // UrgentBloodScreen(navController)
            //CreateEvents(navController)
            //ViewEvents(navController)
           // EventViewExtended(navController)
            //ViewEvents(navController)
            //BloodRequestsScreen(navController)
           // BloodBanks(navController)
           // Login(navController)
           // ProfileScreen(navController)
            //HomeScreen(navController)
          //  MyBookings(navController)

           // EventViewExtended(navController)
           // MyEvents()






        }
    }
}

