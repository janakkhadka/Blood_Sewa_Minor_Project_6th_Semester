package com.example.donation.BottomNavBar




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.donation.ui.theme.RedTop
import com.example.donation.ui.theme.dRed
import java.time.LocalTime



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomTopBar(img :ImageVector, greetingText : String, name : String,text: String,navController : NavHostController) {




    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(RedTop)
            .height(50.dp)
        ,
        contentAlignment = Alignment.Center,

    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically

        ){
            Image(
                imageVector =img,
                contentDescription ="" ,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                )
            Column {
                Text(text = greetingText,fontSize = 14.sp,color = Color.White)
                Text(text = name,fontSize = 12.sp,color = Color.White)

            }

            Text(text = text,fontSize = 22.sp,color = Color.White)

        }


    }

}