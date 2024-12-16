package com.example.donation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.Backend.RegViewModel

import com.example.donation.BottomNavBar.HomeScreen
import com.example.donation.BottomNavBar.UrgentBloodScreen
import com.example.donation.MoreItems.BloodRequestsScreen
import com.example.donation.MoreItems.SearchDonors
import com.example.donation.Navigation.SetUpNavigation
import com.example.donation.Verification.SignUp


class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
         SetUpNavigation(navController)
       // HomeScreen(navController = navController)
            //TopMenuBar()
           // UrgentBloodScreen(navController = navController)
            //SearchDonors()
          //  BloodRequestsScreen()


        }
    }
}

