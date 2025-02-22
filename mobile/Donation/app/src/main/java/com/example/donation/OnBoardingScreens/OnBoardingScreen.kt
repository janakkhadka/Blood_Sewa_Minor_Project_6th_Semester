package com.example.donation.OnBoardingScreens

import android.content.Intent
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bloodsewa.WebSocketManager
import com.example.donation.Navigation.Screens
import kotlinx.coroutines.launch


@Composable
fun OnBoardingScreen(navController: NavHostController) {
    val pages = listOf(OnboardingModel.onboarding1, OnboardingModel.onboarding2, OnboardingModel.onboarding3)
    val pageState = rememberPagerState(initialPage = 0) { pages.size }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //notification
    val webSocketUrl = "wss://s13712.nyc1.piesocket.com/v3/1?api_key=v4WYtVWBmAcBPxYckPQKD8qp5TLX1AhnmQCwQjw4&notify_self=1"
    val webSocketManager = remember { WebSocketManager(context, webSocketUrl) }
    var message by remember { mutableStateOf("No messages yet") }
    LaunchedEffect(Unit) {
        webSocketManager.connect {
            Log.d("abcd", "connected successfully")
            message = it
        }
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(10.dp, 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IndicatorOne(totalPage = pages.size, currentPage = pageState.currentPage)
                ButtonOne(text = if (pageState.currentPage < pages.size - 1) "NEXT" else "GET STARTED", 1f) {
                    scope.launch {
                        if (pageState.currentPage < pages.size - 1) {
                            pageState.animateScrollToPage(pageState.currentPage + 1)
                        } else {


                            navController.navigate(Screens.anynomous.route)
                        }
                    }
                }
            }
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                HorizontalPager(state = pageState) { index ->
                    OnboardingShow(onBoardingModel = pages[index])
                }
            }
        }
    )
}
