package com.example.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodsewa.WebSocketService
import com.example.donation.BottomNavBar.UrgentBloodScreen
import com.example.donation.ExtraItems.CreateEvents
import com.example.donation.ExtraItems.MyEvents
import com.example.donation.Navigation.SetUpNavigation
import com.example.donation.OnBoardingScreens.AnonymousUser


class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val intent = Intent(this, WebSocketService::class.java)
            startService(intent)
            navController = rememberNavController()
            SetUpNavigation(navController)
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
          // MyEvents(navController)
           // UrgentBloodScreen(navController)
           // CreateEvents(navController)
            //
        //AnonymousUser(navController)






        }
    }
}

