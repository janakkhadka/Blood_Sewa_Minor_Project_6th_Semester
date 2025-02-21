package com.example.donation.OnBoardingScreens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.donation.Navigation.Screens
import kotlinx.coroutines.launch


@Composable
fun OnBoardingScreen(navController : NavHostController){
    val pages  = listOf(OnboardingModel.onboarding1,OnboardingModel.onboarding2,OnboardingModel.onboarding3)
    val pageState = rememberPagerState(initialPage = 0){
        pages.size
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier
                .padding(10.dp, 20.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                IndicatorOne(totalPage = pages.size, currentPage =pageState.currentPage )
                ButtonOne(text = if(pageState.currentPage < pages.size -1) "NEXT" else "GET STARTED",1f) {
                    scope.launch {
                        if(pageState.currentPage < pages.size -1){

                            pageState.animateScrollToPage(pageState.currentPage + 1)
                        }else{
                            navController.navigate(Screens.anynomous.route)
                        }
                    }

                }

            }

        },
         content = {
             Column(modifier  = Modifier.padding(it)) {
             HorizontalPager(state = pageState) { index ->
                 OnboardingShow(onBoardingModel = pages[index])
             }
         }

        }

    )


    
}

