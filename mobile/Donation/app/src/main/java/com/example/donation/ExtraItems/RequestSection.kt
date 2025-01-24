package com.example.donation.ExtraItems

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.DataClasses.SeeBloodRequest
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.dRed

@Composable
fun BloodRequestsScreen(navController : NavHostController,viewModel: SharedViewModel = viewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("New Request", "Old Request")
    LaunchedEffect(Unit) {
        viewModel.fetchBloodRequests()
    }
    val bloodRequests by viewModel.bloodRequests.collectAsState()

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
            0 -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp,bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(bloodRequests){requests ->
                        NewRequestTab(requests)

                }

                }
            }
            1 -> OldRequestTab()
        }
    }
}
@Composable
fun NewRequestTab(dasta : SeeBloodRequest) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth(.95f)
            .clip(shape = RoundedCornerShape(10.dp))
            .shadow(elevation = 180.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(dRed),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dasta.blood_group,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = " Name : ${dasta.user_name}")
                Text(text = " Phone Number : ${dasta.contact}")
                Text(text = " Address : ${dasta.location}")
                androidx.compose.material3.Button(
                    onClick = {
                        val number = dasta.contact
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$number")
                        }
                        context.startActivity(intent)

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(DarkGreen),

                    ) {
                    Text(text = "Contact")
                }



            }



        }
    }

}

@Composable
fun OldRequestTab() {

    Column {
        Text("List of old blood requests", color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun ShowData(){
    val navController = rememberNavController()
    BloodRequestsScreen(navController)
}


