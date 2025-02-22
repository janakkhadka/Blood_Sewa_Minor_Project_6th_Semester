package com.example.donation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.Navigation.SetUpNavigation
import com.example.donation.OnBoardingScreens.AnonymousUser


class MainActivity : ComponentActivity() {
    lateinit var navController : NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            createNotificationChannel()
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
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Blood Sewa"
            val descriptionText = "Urgent Requirement"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("websocket_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}

