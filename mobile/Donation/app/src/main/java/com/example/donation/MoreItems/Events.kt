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
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme


@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun Events(navController : NavHostController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name ="" , text ="Events" ,navController)

    }
}