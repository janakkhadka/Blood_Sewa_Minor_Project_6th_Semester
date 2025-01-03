package com.example.donation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.ProfileScreen



class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
       // SetUpNavigation(navController)
       // HomeScreen(navController = navController)
            //TopMenuBar()
           // UrgentBloodScreen(navController = navController)
            //SearchDonors()
          //  BloodRequestsScreen()
            //SignUp(navController = navController)
           // CreateEvents()
            //ScheduleTime()
           // ViewEvents()
            //ViewEvents()
            ProfileScreen(navController)


        }
    }
}

