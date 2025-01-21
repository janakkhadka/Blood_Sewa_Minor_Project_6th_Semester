package com.example.donation.BottomNavBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Urgent Blood",Icons.Default.WaterDrop),
        NavItem("Profile",Icons.Default.Person2)
    )
    var selected by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = white, tonalElevation = 20.dp){
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected =selected == index ,
                        onClick = {
                            selected = index
                                  },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription ="Icon" )
                        },
                        label = {Text(text =navItem.label)},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = dRed,

                        )


                    )


                }
            }
        }
    ) { paddingValues ->

        Content(navController,paddingValues = paddingValues,selected)
    }
}

@Composable
fun Content(navController : NavHostController ,paddingValues: PaddingValues,selected : Int) {
    when(selected){
        0-> HomeScreen(navController)
        1-> UrgentBloodScreen(navController)
        2-> ProfileScreen(navController = navController)
    }


}

