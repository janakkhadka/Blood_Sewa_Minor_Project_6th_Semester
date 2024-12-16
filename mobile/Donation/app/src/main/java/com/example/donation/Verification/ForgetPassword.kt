package com.example.donation.Verification

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.donation.Navigation.Screens
import com.example.donation.R
import com.example.donation.ui.theme.dRed



@Composable
fun ForgetPassword(navController : NavHostController){
    var numberOrEmail by remember{ mutableStateOf("") }

    Row(horizontalArrangement = Arrangement.spacedBy(90.dp),modifier = Modifier.padding(top =40.dp)){
        FloatingActionButton(onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.padding(start = 10.dp),
            containerColor = dRed,
            contentColor = Color.White
        ) {
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = "")

        }

        Text(text ="Forget Password",
            fontSize = 20.sp,
            style = TextStyle.Default,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 13.dp)
        )
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp), horizontalAlignment = Alignment.CenterHorizontally){

        Spacer(modifier = Modifier.height(40.dp))
        Image(painter = painterResource(id = R.drawable.forget),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.padding(30.dp))
        Text(text = "Please Enter your email or phone number to receive a verification code",
            modifier = Modifier.padding(start = 30.dp,end = 40.dp),
            fontSize = 16.sp
            )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "Email or Phone number")
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        OutlinedTextField(
            value = numberOrEmail,
            onValueChange = {numberOrEmail = it}
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Button(onClick = { navController.navigate(Screens.ChangePassword.route)},
            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = dRed),
            modifier = Modifier
                .fillMaxWidth(.8f)
                .height(40.dp)

        ) {
            Text(text = "SEND", color = Color.White, fontSize = 16.sp)
            
        }

    }
}
