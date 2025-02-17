package com.example.donation.Navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.donation.BottomNavBar.BottomNavBar
import com.example.donation.BottomNavBar.ProfileScreen
import com.example.donation.BottomNavBar.UpdateProfile
import com.example.donation.ExtraItems.CreateEvents
import com.example.donation.ExtraItems.BloodBanks
import com.example.donation.ExtraItems.BloodRequestsScreen
import com.example.donation.ExtraItems.EventViewExtended
import com.example.donation.ExtraItems.MyBookings
import com.example.donation.ExtraItems.MyEvents
import com.example.donation.ExtraItems.SearchDonors
import com.example.donation.OnBoardingScreens.OnBoardingScreen
import com.example.donation.Verification.ChangePassword
import com.example.donation.Verification.ForgetPassword
import com.example.donation.Verification.Login
import com.example.donation.Verification.OtpVerification
import com.example.donation.Verification.SignUp
import com.example.donation.datastore.DataStoreManager
import com.example.donation.ExtraItems.ScheduleTime


@Composable
fun SetUpNavigation(navController : NavHostController) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val checkStatus by dataStoreManager.getStatus.collectAsState(initial = null)

    if (checkStatus == null) {
        LoadingScreen()
    } else {
        val startDestination = if (checkStatus == true) Screens.BottomNavBar.route else Screens.OnBoarding.route


        NavHost(
            navController = navController,
            startDestination = Screens.OnBoarding.route
        ) {
            composable(Screens.OnBoarding.route) {
                OnBoardingScreen(navController)
            }
            composable(Screens.Login.route) {
                Login(navController)
            }
            composable(Screens.Signup.route) {
                SignUp(navController)
            }
            composable(route = Screens.OtpVerification.route) {
                OtpVerification(navController)
            }
            composable(route = Screens.ChangePassword.route) {
                ChangePassword(navController)
            }
            composable(route = Screens.ForgetPassword.route) {
                ForgetPassword(navController)
            }
            composable(route = Screens.BottomNavBar.route) {
                BottomNavBar(navController)
            }

            composable(route = Screens.RequestSection.route) {
                BloodRequestsScreen(navController)
            }

            composable(route = Screens.BloodBanks.route) {
                BloodBanks(navController)
            }

            composable(route = Screens.SearchDonors.route) {
                SearchDonors(navController)
            }



            composable(route = Screens.createEvents.route) {
                CreateEvents(navController)
            }

            composable(Screens.schedultTime.route) {
                ScheduleTime(navController)
            }
            composable(Screens.eventViewExtended.route){
                EventViewExtended(navController)
            }
            composable(Screens.myBookings.route) {
                MyBookings(navController)
            }

            composable(Screens.MyEvents.route) {
                MyEvents(navController)
            }

            composable(Screens.updateProfile.route) {

                UpdateProfile(navController)
            }



        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()

    }
}
