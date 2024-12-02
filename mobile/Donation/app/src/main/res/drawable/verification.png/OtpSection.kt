package com.example.donation.Verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun OtpVerification(){
    Column(modifier = Modifier.fillMaxSize()){
        Row(horizontalArrangement = Arrangement.spacedBy(100.dp),modifier = Modifier.padding(top =40.dp,start = 10.dp)){
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            Text(text ="Forget Password",
                fontSize = 20.sp,
                style = TextStyle.Default,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Image(painter = , contentDescription = )

    }
}
