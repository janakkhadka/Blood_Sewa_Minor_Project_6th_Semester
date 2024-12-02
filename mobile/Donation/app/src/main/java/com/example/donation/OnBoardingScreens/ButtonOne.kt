package com.example.donation.OnBoardingScreens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.donation.ui.theme.dRed


@Composable
fun ButtonOne(
    text: String,
    buttonsize : Float,
    backgroundColor: Color = dRed,
    textColor: Color = Color.White,
    fontSize: Int = 14,
    onClick: () -> Unit

) {
    Button(
        onClick = onClick,
        colors = androidx.compose.material.ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(buttonsize)
            .height(40.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize.sp
        )
    }
}









