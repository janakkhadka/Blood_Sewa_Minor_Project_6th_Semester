package com.example.donation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.Route
import com.example.donation.BottomNavBar.UrgentBloodScreen
import com.example.donation.ExtraItems.CreateEvents
import com.example.donation.ExtraItems.ScheduleTime
import com.example.donation.Navigation.Screens
import com.example.donation.Navigation.SetUpNavigation
import com.example.donation.TestingPurpose.ShowTestValue


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
            //ScheduleTime(navController)
            UrgentBloodScreen(navController)
            //CreateEvents(navController)





        }
    }
}

