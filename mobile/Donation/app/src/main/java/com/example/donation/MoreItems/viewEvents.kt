package com.example.donation.moreItems



import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewEvents() {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Column {
                    TopBarTheme()
                    CustomTopBar(Icons.Default.ArrowBack, "", "", "Events", navController)
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    },
                    backgroundColor = dRed
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = "Scan QR Code",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        ) {

        }
    }
}

