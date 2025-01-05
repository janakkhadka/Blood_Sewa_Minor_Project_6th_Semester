package com.example.donation.MoreItems

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme


@Composable
fun BloodBanks(navController : NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name ="" , text ="Blood Banks",navController )


    }
}

@Preview(showBackground = true)
@Composable
fun PShow(){
    val navController = rememberNavController()
    BloodBanks(navController)
}
