package com.example.donation.BottomNavBar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.Navigation.Screens
import com.example.donation.datastore.DataStoreManager
import com.example.donation.ui.theme.RedThemeTop
import java.time.LocalTime



@Composable
fun HomeScreen(navController : NavHostController ) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val accessToken by dataStoreManager.getAccessToken.collectAsState(initial = null)

    LaunchedEffect(accessToken){
        Log.d("accesstoken","$accessToken")

    }

    val hour = LocalTime.now().hour
    var greetingText = when (hour) {
        in 1..12 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"


    }
    Column() {
        TopBarTheme()
        CustomTopBar(Icons.Default.Person, greetingText, "Kiran Acharya", "Blood Sewa",navController)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
            ,
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = " Urgent Requests",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(200.dp)
                        .shadow(elevation = 50.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)

                ) {
                    //urgent request haru main home page ma dekhaune paryo vhane yeta dekhune ho
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "More Items",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
                // Inside the Box for "More Items"
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(200.dp)
                        .shadow(elevation = 50.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // First row of items
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconWithLabel(Icons.Default.EventAvailable, "Events")
                            {
                                navController.navigate(Screens.Events.route)
                                Log.d("token","$accessToken")
                            }
                            IconWithLabel(Icons.Default.PersonAddAlt1, "Search Donor")
                            {
                                navController.navigate(Screens.SearchDonors.route)
                            }
                            IconWithLabel(Icons.Default.LocalHospital, "Blood Banks")
                            {
                                navController.navigate(Screens.BloodBanks.route)
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        // Second row of items
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconWithLabel(Icons.Default.PlaylistAdd, "Request")
                            {
                                navController.navigate(Screens.RequestSection.route)
                            }
                            IconWithLabel(Icons.Default.FindInPage, "Find Blood")
                            {
                                navController.navigate(Screens.Events.route)
                            }
                            IconWithLabel(Icons.Default.EventAvailable, "Other Events")
                            {
                                navController.navigate(Screens.Events.route)
                            }
                        }
                    }
                }
                Text(
                    text = "Events Happening",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(200.dp)
                        .shadow(elevation = 50.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 60.dp)

                ) {
                    //urgent request haru main home page ma dekhaune paryo vhane yeta dekhune ho
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}


@Composable
fun IconWithLabel(icon: ImageVector, label: String,onClick :() ->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Image(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(35.dp),
            colorFilter = ColorFilter.tint(RedThemeTop)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}





@Preview(showBackground =true)
@Composable
fun Preview(){
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
