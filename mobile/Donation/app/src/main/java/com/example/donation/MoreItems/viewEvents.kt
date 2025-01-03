package com.example.donation.moreItems

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground =true )
@Composable
fun ViewEvents(){
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Scaffold(
            topBar = {
                Column() {
                    TopBarTheme()
                    CustomTopBar(Icons.Default.ArrowBack, "", "", "Events", navController)
                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    backgroundColor = dRed
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White

                        )
                }
            }
        ) {

        }

    }


}
