package com.example.donation.moreitems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed


@Preview(showBackground = true)
@Composable
fun ScheduleTime(){
    val navController = rememberNavController()
    var provinceExpanded by remember { mutableStateOf(false) }
    var provinceSelected by remember { mutableStateOf("") }
    var hospitalExpanded by remember { mutableStateOf(false) }
    var hospitalSelected by remember { mutableStateOf("") }
    var shiftExpanded by remember { mutableStateOf(false) }
    var shiftSelected by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var donation_date by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    //tala gayera data haru laii show garne kaam
    val request = remember { mutableStateListOf<String>() }
    val nepalProvinces = listOf(
        "Province No. 1",
        "Madhesh Province",
        "Bagmati Province",
        "Gandaki Province",
        "Lumbini Province",
        "Karnali Province",
        "Sudurpashchim Province"
    )
    val hospitals = listOf("KMC Hospital","Civil Hospital","B&B Hospital")
    val shifts = listOf("Morning (9am - 12am","Afternoon (12pm - 4pm)","Evening (4pm - 8pm)")
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "Book Donation",navController)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .height(170.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(dRed)
            ) {}

        }
        Box(
            modifier = Modifier
                .padding(top = 130.dp)
                .shadow(elevation = 50.dp)
                .fillMaxWidth(.9f)
                .align(Alignment.TopCenter)
                .clip(shape = RoundedCornerShape(40.dp))
                .background(Color.White),
            contentAlignment = Alignment.TopCenter

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.8f)
                        .clickable { provinceExpanded = true },
                    value = provinceSelected,
                    onValueChange = { provinceSelected = it },
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Select Province") },
                    trailingIcon = {
                        Icon(
                            imageVector = if (provinceExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = if (provinceExpanded) "Collapse Dropdown" else "Expand Dropdown",
                            modifier = Modifier.clickable { provinceExpanded = !provinceExpanded }
                        )
                    },

                    )
                DropdownMenu(
                    expanded = provinceExpanded,
                    onDismissRequest = { provinceExpanded = false },
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    nepalProvinces.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option)
                            },
                            onClick = {
                                provinceSelected = option
                                provinceExpanded = false
                            })

                    }
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.8f)
                        .clickable { hospitalExpanded= true },
                    value = hospitalSelected,
                    onValueChange = { hospitalSelected = it },
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Hospital/Blood Banks") },
                    trailingIcon = {
                        Icon(
                            imageVector = if (hospitalExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = if (hospitalExpanded) "Collapse Dropdown" else "Expand Dropdown",
                            modifier = Modifier.clickable { hospitalExpanded = !hospitalExpanded }
                        )
                    },

                    )
                DropdownMenu(
                    expanded = hospitalExpanded,
                    onDismissRequest = { hospitalExpanded = false },
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    hospitals.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option)
                            },
                            onClick = {
                                hospitalSelected = option
                                hospitalExpanded = false
                            })

                    }
                }

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.8f)
                        .clickable { shiftExpanded = true },
                    value = shiftSelected,
                    onValueChange = { shiftSelected = it },
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Select shift") },
                    trailingIcon = {
                        Icon(
                            imageVector = if (shiftExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = if (shiftExpanded) "Collapse Dropdown" else "Expand Dropdown",
                            modifier = Modifier.clickable { shiftExpanded = !shiftExpanded }
                        )
                    },

                    )
                DropdownMenu(
                    expanded = shiftExpanded,
                    onDismissRequest = { shiftExpanded = false },
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    shifts.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option)
                            },
                            onClick = {
                                shiftSelected = option
                                shiftExpanded = false
                            })

                    }
                }
                OutlinedTextField(
                    value = donation_date,
                    onValueChange = { donation_date = it },
                    label = { Text("Preferred Donation date") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.8f),
                    )
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth(.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = dRed,
                        contentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(5.dp)

                ) {
                    Text(text = "Schedule Time", color = Color.White, fontSize = 22.sp)

                }
            }


            }





    }

}


