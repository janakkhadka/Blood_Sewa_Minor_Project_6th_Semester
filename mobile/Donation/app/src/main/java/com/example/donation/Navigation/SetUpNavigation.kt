package com.example.donation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.donation.BottomNavBar.BottomNavBar
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.OnBoardingScreens.OnBoardingScreen
import com.example.donation.Verification.ChangePassword
import com.example.donation.Verification.ForgetPassword
import com.example.donation.Verification.Login
import com.example.donation.Verification.OtpVerification
import com.example.donation.Verification.SignUp

@Composable
fun SetUpNavigation(navController : NavHostController) {
    val navController = rememberNavController()

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
        composable(route = Screens.OtpVerification.route ) {
            OtpVerification(navController)
        }
        composable(route = Screens.ChangePassword.route ) {
            ChangePassword(navController)
        }
        composable(route = Screens.ForgetPassword.route ) {
            ForgetPassword(navController)
        }
        composable(route = Screens.BottomNavBar.route ) {
            BottomNavBar(navController)
        }

        composable(route = Screens.CustomTopBar.route ) {
            CustomTopBar(navController)
        }

    }
}
