package com.example.donation.BottomNavBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.ui.theme.RedThemeTop
import com.example.donation.ui.theme.RedTop
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white


@Composable
fun HomeScreen(navController : NavHostController,) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(white), contentAlignment = Alignment.TopCenter) {
        Column {
            TopBarTheme()
            CustomTopBar(navController)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 10.dp)
        ,
        contentAlignment = Alignment.Center) {


    Card(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .fillMaxSize()
            .align(Alignment.Center),
        colors = CardDefaults.cardColors(
            containerColor = dRed
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        //card content
    }

}
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .fillMaxSize(),
                    shape = RoundedCornerShape(10.dp)
                ){

                }

            }



        }
    }
}

@Preview(showBackground =true)
@Composable
fun Preview(){
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
