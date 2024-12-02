package com.example.donation.Verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.donation.R
import com.example.donation.ui.theme.dRed



@Composable
fun ChangePassword(navController : NavHostController){
    var newPassword by remember{ mutableStateOf("") }
    var confirmPassword by remember{ mutableStateOf("") }


    Row(horizontalArrangement = Arrangement.spacedBy(90.dp),modifier = Modifier.padding(top =40.dp)){
        FloatingActionButton(onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.padding(start = 10.dp),
            containerColor = dRed,
            contentColor = Color.White
        ) {
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = "")

        }

        Text(text ="Change Password",
            fontSize = 20.sp,
            style = TextStyle.Default,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 13.dp)
        )
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.change),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Enter new password",
            modifier = Modifier.padding(start = 30.dp, end = 40.dp),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.padding(3.dp))
        OutlinedTextField(
            value = newPassword ,
            onValueChange ={newPassword = it} )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Confirm password",
            modifier = Modifier.padding(start = 30.dp, end = 40.dp),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.padding(3.dp))
        OutlinedTextField(
            value = confirmPassword ,
            onValueChange ={confirmPassword = it} )

        Spacer(modifier = Modifier.padding(top = 30.dp))


        Spacer(modifier = Modifier.padding(top = 20.dp))
        Button(
            onClick = { },
            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = dRed
            ),
            modifier = Modifier
                .fillMaxWidth(.8f)
                .height(40.dp)

        ) {
            Text(text = "CHANGE", color = Color.White, fontSize = 16.sp)

        }

    }
}



