package com.example.donation.BottomNavBar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.ui.theme.RedTop
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UrgentBloodScreen(navController : NavHostController) {
    var Bloodexpanded by remember { mutableStateOf(false) }
    var Bloodselected by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    //tala gayera data haru laii show garne kaam
    val request = remember { mutableStateListOf<String>() }
    val bloodOptions = listOf("A+", "B+", "A-", "B-", "O+", "O-", "AB+", "AB-")
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopBarTheme()
            CustomTopBar(Icons.Default.WaterDrop, "", "", "Urgent Blood Sewa",navController)
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
                .height(300.dp)
                .shadow(elevation = 50.dp)
                .fillMaxWidth(.8f)
                .align(Alignment.TopCenter)
                .clip(shape = RoundedCornerShape(40.dp))
                .background(Color.White),
            contentAlignment = Alignment.TopCenter

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.8f),
                    value = Bloodselected,
                    onValueChange = { Bloodselected = it },
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Blood Group") },
                    trailingIcon = {
                        Icon(
                            imageVector = if (Bloodexpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                            contentDescription = if (Bloodexpanded) "Collapse Dropdown" else "Expand Dropdown",
                            modifier = Modifier.clickable { Bloodexpanded = !Bloodexpanded }
                        )
                    },

                    )
                DropdownMenu(
                    expanded = Bloodexpanded,
                    onDismissRequest = { Bloodexpanded = false },
                    modifier = Modifier.fillMaxWidth(.8f)
                ) {
                    bloodOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(text = option)
                            },
                            onClick = {
                                Bloodselected = option
                                Bloodexpanded = false
                            })

                    }
                }
                OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    label = { Text("Enter address") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.8f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = ""
                        )
                    }

                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Enter contact") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.8f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = ""
                        )
                    }
                )


            }
        }
        Button(
            onClick = {
                if (Bloodselected.isNotEmpty() && contact.isNotEmpty() && address.isNotEmpty()) {
                    request.add("Blood Group: $Bloodselected\nContact: $contact\nAddress: $address")
                }
            },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 40.dp)
                .fillMaxWidth(.7f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = dRed,
                contentColor = Color.Gray
            ),
            shape = RoundedCornerShape(5.dp)

        ) {
            Text(text = "Update", color = Color.White, fontSize = 22.sp)

        }
        Text(
            text = "Requests",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(top = 150.dp, start = 30.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 160.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            request.forEach { request ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = request,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
            }

        }

    }
}








@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Show() {
    val navController = rememberNavController()
    UrgentBloodScreen(navController = navController)

}