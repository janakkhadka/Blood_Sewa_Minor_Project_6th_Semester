package com.example.donation.ExtraItems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.NavItem
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.DataClasses.MyBookings
import com.example.donation.Navigation.Screens
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white


@Composable
fun MyBookings(navController: NavHostController,viewModel: SharedViewModel = viewModel()){
   LaunchedEffect(Unit) {
       viewModel.fetchMyBookings()
   }

    val myBookingsList by viewModel.myBookings.collectAsState()

    Box() {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "My Bookings", navController)
            myBookingsList.forEach { data ->
                DataToShow(data)

            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate(Screens.schedultTime.route)
            },
            backgroundColor = dRed,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Scan QR Code",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }

}

@Composable
fun DataToShow(data : MyBookings){
    Box(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .padding(top = 20.dp)
            .shadow(elevation = 40.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White),
        

    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 15.dp,top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text("Organization : ${data.organization} ")
            Text("Booking Date : ${data.booking_date} ")
            Text("Shift : ${data.shift} ")
        }


    }

}