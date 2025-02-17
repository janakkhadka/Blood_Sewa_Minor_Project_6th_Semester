package com.example.donation.ExtraItems


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.donation.DataClasses.MyCreatedEvent
import com.example.donation.DataClasses.MyJoinedEvents
import com.example.donation.R
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.dRed

@Composable
fun MyEvents( navController : NavHostController,viewModel: SharedViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.fetchEventHistory()
    }
    LaunchedEffect(Unit) {
        viewModel.fetchCreatedEvents()
    }


    val joinedEvents by viewModel.historyListJoined.collectAsState()
    val createdEvents by viewModel.createdEvents.collectAsState()

    // Tab state
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("My Events", "Joined Events")
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Top bar
            Column {
                TopBarTheme()
                CustomTopBar(Icons.Default.ArrowBack, "", "", "My Events", navController)
            }

            // Tabs
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
                tabTitles.forEachIndexed { index, title ->
                    androidx.compose.material3.Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            androidx.compose.material3.Text(
                                title,
                                color = if (selectedTabIndex == index) dRed
                                else MaterialTheme.colors.onBackground
                            )
                        }
                    )
                }
            }

            // Tab content
            when (selectedTabIndex) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp,bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(createdEvents){requests ->
                            MyCreatedEvents(requests)

                        }

                    }
                }
                1 ->  {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp,bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(joinedEvents){requests ->
                            MyJoinedEvents(requests)

                        }

                    }
                }
            }


        }

    }


}

@Composable
fun MyCreatedEvents(data : MyCreatedEvent) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 100.dp)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(White)
            .clickable {
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.donate),
                contentDescription = "",
                modifier = Modifier.size(60.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Name: ${data.name}")
                Text("Date: ${data.date}")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    Text(text = "Volunteered :")
                    Text(text = data.collabrator_name, color = DarkGreen )
                }
            }


        }
    }
}

@Composable
fun MyJoinedEvents(data : MyJoinedEvents) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 100.dp)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(White)
            .clickable {
                showDialog = true
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.donate),
                contentDescription = "",
                modifier = Modifier.size(60.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Name: ${data.name}")
                Text("Date: ${data.date}")
                Text("Location: ${data.location}")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    Text(text = "Status :")
                    Text(text = "${data.status}", color = DarkGreen )
                }
            }


        }
    }
}



