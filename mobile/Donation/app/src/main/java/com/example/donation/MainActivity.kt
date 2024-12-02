package com.example.donation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.donation.BottomNavBar.HomeScreen
import com.example.donation.Navigation.SetUpNavigation


class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
           SetUpNavigation(navController)
        //HomeScreen(navController = navController)
            //TopMenuBar()




        }
    }
}

