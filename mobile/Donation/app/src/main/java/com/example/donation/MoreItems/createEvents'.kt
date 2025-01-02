package com.example.donation.moreItems

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.RootGroupName
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed

@Preview(showBackground = true)
@Composable
fun CreateEvents(){
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "Create Events",navController)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp))
                    .background(dRed)
            ) {}

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(top = 160.dp)
                .height(400.dp)
                .shadow(elevation = 40.dp)
                .clip(shape = RoundedCornerShape(40.dp))
                .background(Color.White)
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {}
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(top =530.dp)
                .fillMaxWidth(.5f)
                .height(50.dp)
                .shadow(elevation = 20.dp),
            colors = ButtonDefaults.buttonColors(dRed),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "CREATE", fontSize = 22.sp)
        }

    }
}

