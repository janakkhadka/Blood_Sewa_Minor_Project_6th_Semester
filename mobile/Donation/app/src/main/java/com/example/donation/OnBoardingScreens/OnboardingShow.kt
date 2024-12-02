package com.example.donation.OnBoardingScreens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingShow(onBoardingModel : OnboardingModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = onBoardingModel.image),
            contentDescription ="",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
                .size(300.dp),
            alignment = Alignment.Center
        )
        Text(
            text = onBoardingModel.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                ,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold
        )


        Text(
            text = onBoardingModel.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,

        )

    }






}


@Preview(showBackground = true)
@Composable
fun OnBoarding1(){
    OnboardingShow(OnboardingModel.onboarding1)
}

@Preview(showBackground = true)
@Composable
fun OnBoarding2(){
    OnboardingShow(OnboardingModel.onboarding2)
}

@Preview(showBackground = true)
@Composable
fun OnBoarding3(){
    OnboardingShow(OnboardingModel.onboarding3)
}