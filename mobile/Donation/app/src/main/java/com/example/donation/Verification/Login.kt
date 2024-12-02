package com.example.donation.Verification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.donation.Navigation.Screens
import com.example.donation.ui.theme.dRed

@Composable
fun Login(navController : NavHostController){
    var username by remember{ mutableStateOf("") }
    var phoneNumber by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "LOGIN",
            modifier = Modifier.padding(top = 100.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.padding(100.dp))


        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            leadingIcon = {
                Text(text = "+977")
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon ={
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = if(isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = if(isPasswordVisible) "Hide password" else "show password")

                }
            }
            ,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Account Circle Icon"
                )
            }
        )
        Text(text = "Forget Password?", modifier = Modifier.padding(start = 160.dp)
            .clickable { navController.navigate(Screens.ForgetPassword.route) }
        )


        Button(onClick = { },
            colors =androidx.compose.material3. ButtonDefaults.buttonColors(
                containerColor = dRed,
                contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth(.8f)
                .height(40.dp),
            shape = RoundedCornerShape(8.dp)


        ) {
            Text(text = "SIGN UP", color = Color.White, fontSize = 18.sp)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){
            Text(text = "Don't have account?")
            Text(
                text = "Sign up",
                color = dRed,
                modifier = Modifier
                    .clickable {
                       navController.navigate(Screens.Signup.route)
                    }
            )
        }




    }


}



