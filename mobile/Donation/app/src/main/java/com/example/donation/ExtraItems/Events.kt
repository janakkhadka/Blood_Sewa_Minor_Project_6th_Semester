package com.example.donation.ExtraItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.Navigation.Screens
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white


@Composable
fun Events(navController : NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name ="" , text ="Events" ,navController)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 20.dp,end = 10.dp),
            contentAlignment = Alignment.BottomEnd
        ){
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.createEvents.route)
                },
                backgroundColor = dRed
            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add event",
                    colorFilter = ColorFilter.tint(white)
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun Show(){
    val navController = rememberNavController()
    Events(navController)
}