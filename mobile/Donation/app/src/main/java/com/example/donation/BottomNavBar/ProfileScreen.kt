package com.example.donation.BottomNavBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.dRed


@Composable
fun ProfileScreen(navController : NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopBarTheme()
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                    )


                }
            }
            Button(
                onClick = {  },
                modifier = Modifier
                    .height(40.dp)
                ,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    DarkGreen
                ),

            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Default.Add,
                        contentDescription ="",
                        colorFilter = ColorFilter.tint(Color.White)

                    )

                    Text(
                        text = "Add Photo",

                        )
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .height(200.dp)
                    .shadow(elevation = 50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White)

                ,
                contentAlignment = Alignment.TopStart
            ){
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(text = " Personal Details", fontSize = 20.sp)
                    Text(text = "Name : Kiran acharya", fontSize = 16.sp)
                    Text(text = "Blood Group : B+", fontSize = 16.sp)
                    Text(text = "Address : karnali province,kalikot", fontSize = 16.sp)
                    Text(text = "Contact : 985445343", fontSize = 16.sp)
                    Text(text = "DOB: 2060-02-12", fontSize = 16.sp)
                }
            }


        }



    }
}

@Preview(showBackground = true)
@Composable
fun show(){
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}