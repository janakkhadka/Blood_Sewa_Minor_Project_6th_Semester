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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Transgender
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
            modifier = Modifier.fillMaxWidth()
        ) {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack,"","","Profile Section",navController)

            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp,top = 10.dp),

                ) {
                    Text(text = "User Information", fontSize = 20.sp)
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.spacedBy(10.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ){
                       Image(
                           Icons.Default.PersonOutline,
                           contentDescription = "",
                           modifier = Modifier.size(60.dp)
                       )
                       Column() {
                           Text(text = "Kiran Acharya", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                           Text(text = "kiran.211720@ncit.edu.np", fontSize = 16.sp)
                       }
                   }

                    Details(Icons.Default.CalendarMonth,"Date of Birth","2003")
                    Details(Icons.Default.CalendarMonth,"Gender","Male")
                    Details(Icons.Default.Phone,"Phone","+977-98675445343")
                    Details(Icons.Default.Home,"Address","Kalikot,Karnali Province")

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(dRed),


                            ) {
                            Text(text = "Update Profile", fontSize = 18.sp)
                        }
                    }
                }
            }
        }

    }



}

@Composable
fun Details(
    Icons : ImageVector,
    text : String,
    value : String
){
    Row(
        modifier = Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            Icons,
            contentDescription = ""
        )
        Row(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = "$text:")
            Text(text = value)

        }
    }


}


@Preview(showBackground = true)
@Composable
fun show(){
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}