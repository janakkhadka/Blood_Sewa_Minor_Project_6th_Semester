package com.example.donation.BottomNavBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationGraph(navController : NavHostController,onBottomBarVisibilityChanged : (Boolean) -> Unit)
{
    NavHost(navController = navController, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            onBottomBarVisibilityChanged(true)
            HomeScreen(navController)
        }

        composable(Route.Profile.route) {
            onBottomBarVisibilityChanged(true)
            ProfileScreen(navController)
        }

        composable(Route.Urgent.route) {
            onBottomBarVisibilityChanged(true)
            UrgentBloodScreen(navController)
        }

    }
}