package com.example.donation.OnBoardingScreens

import com.example.donation.R


sealed class OnboardingModel(
     val image : Int,
     val title : String,
     val description : String

 ) {
     data object onboarding1 : OnboardingModel(
         image =  R.drawable.blood,
         title = "URGENTLY FIND BLOOD",
         description = "Now you can easily find and donate blood through this app"
     )
    data object onboarding2 : OnboardingModel(
        image =  R.drawable.event,
        title = "ENGAGE AND DONATE BLOOD",
        description = "Now you can easily find events and donate blood through this app"
    )

    data object onboarding3 : OnboardingModel(
        image =  R.drawable.bank,
        title = "FIND BLOOD BANKS",
        description = "Now you can easily find blood banks and hospitals nearby through this app"
    )
}