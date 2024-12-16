package com.example.donation.MoreItems

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BloodRequestsScreen(navController : NavHostController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("New Request", "Old Request")

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name = "", text = "Requests",navController)


        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colors.error
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            title,
                            color = if (selectedTabIndex == index) dRed
                            else MaterialTheme.colors.onBackground
                        )
                    }
                )
            }
        }


        when (selectedTabIndex) {
            0 -> NewRequestTab()
            1 -> OldRequestTab()
        }
    }
}
@Composable
fun NewRequestTab() {

    Column {
        Text("List of new blood requests", color = Color.Black)
    }
}

@Composable
fun OldRequestTab() {

    Column {
        Text("List of old blood requests", color = Color.Black)
    }
}