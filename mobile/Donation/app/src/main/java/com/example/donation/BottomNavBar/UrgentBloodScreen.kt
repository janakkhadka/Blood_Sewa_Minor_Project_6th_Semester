package com.example.donation.BottomNavBar


import android.util.Log
import android.widget.Toast
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

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.DataClasses.BloodRequest
import com.example.donation.DataClasses.ScheduleTime
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.RedTop
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white



@Composable
fun UrgentBloodScreen(navController : NavHostController, viewModel: SharedViewModel = viewModel()) {
    var Bloodexpanded by remember { mutableStateOf(false) }
    var Bloodselected by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var patient_name by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    var Districtexpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var currentDistricts by remember { mutableStateOf(listOf<String>()) }
    LaunchedEffect(Unit) {
        viewModel.fetchDataBloodGroup()
    }

    val nepalProvinces = listOf(
        "Province No. 1",
        "Madhesh Province",
        "Bagmati Province",
        "Gandaki Province",
        "Lumbini Province",
        "Karnali Province",
        "Sudurpashchim Province"
    )

    val province1Districts = listOf(
        "Bhojpur",
        "Dhankuta",
        "Ilam",
        "Jhapa",
        "Khotang",
        "Morang",
        "Okhaldhunga",
        "Panchthar",
        "Sankhuwasabha",
        "Solukhumbu",
        "Sunsari",
        "Taplejung",
        "Terhathum",
        "Udayapur"
    )
    val madheshProvinceDistricts = listOf(
        "Bara",
        "Dhanusha",
        "Mahottari",
        "Parsa",
        "Rautahat",
        "Saptari",
        "Sarlahi",
        "Siraha"
    )
    val bagmatiProvinceDistricts = listOf(
        "Bhaktapur",
        "Chitwan",
        "Dhading",
        "Dolakha",
        "Kathmandu",
        "Kavrepalanchok",
        "Lalitpur",
        "Makawanpur",
        "Nuwakot",
        "Ramechhap",
        "Rasuwa",
        "Sindhuli",
        "Sindhupalchok"
    )
    val gandakiProvinceDistricts = listOf(
        "Baglung",
        "Gorkha",
        "Kaski",
        "Lamjung",
        "Manang",
        "Mustang",
        "Myagdi",
        "Nawalpur",
        "Parbat",
        "Syangja",
        "Tanahun"
    )
    val lumbiniProvinceDistricts = listOf(
        "Arghakhanchi",
        "Banke",
        "Bardiya",
        "Dang",
        "Gulmi",
        "Kapilvastu",
        "Parasi (Nawalparasi West)",
        "Palpa",
        "Pyuthan",
        "Rolpa",
        "Rukum (East)",
        "Rupandehi"
    )
    val karnaliProvinceDistricts = listOf(
        "Dailekh",
        "Dolpa",
        "Humla",
        "Jajarkot",
        "Jumla",
        "Kalikot",
        "Mugu",
        "Rukum (West)",
        "Salyan",
        "Surkhet"
    )
    val sudurpashchimProvinceDistricts = listOf(
        "Achham",
        "Baitadi",
        "Bajhang",
        "Bajura",
        "Dadeldhura",
        "Darchula",
        "Doti",
        "Kailali",
        "Kanchanpur"
    )

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
                .shadow(elevation = 50.dp)
                .fillMaxWidth(.9f)
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
                    value = patient_name,
                    onValueChange = { patient_name = it },
                    label = { Text("Patient Name") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.9f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = ""
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(.9f),
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
                    modifier = Modifier.fillMaxWidth(.9f)
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
                Box(
                    modifier = Modifier.fillMaxWidth(.9f)
                ) {

                    OutlinedTextField(
                        value = province,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Select Province") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = if (expanded) "Collapse Dropdown" else "Expand Dropdown",
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { expanded = true }
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    nepalProvinces.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                province = option
                                expanded = false

                                currentDistricts = when (province) {
                                    "Province No. 1" -> province1Districts
                                    "Madhesh Province" -> madheshProvinceDistricts
                                    "Bagmati Province" -> bagmatiProvinceDistricts
                                    "Gandaki Province" -> gandakiProvinceDistricts
                                    "Lumbini Province" -> lumbiniProvinceDistricts
                                    "Karnali Province" -> karnaliProvinceDistricts
                                    "Sudurpashchim Province" -> sudurpashchimProvinceDistricts
                                    else -> emptyList()
                                }
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    OutlinedTextField(
                        value = district,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Select District") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (Districtexpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )


                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { Districtexpanded = true }
                    )
                }

                DropdownMenu(
                    expanded = Districtexpanded,
                    onDismissRequest = { Districtexpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    currentDistricts.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                district = option
                                Districtexpanded = false
                            }
                        )
                    }
                }
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Enter City") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.9f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = ""
                        )
                    }
                )


                OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    label = { Text("Enter contact") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.9f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = ""
                        )
                    }
                )
                Button(
                    onClick = {



                        try {
                            viewModel.createBloodRequest(patient_name = patient_name,contact,Bloodselected,province,district,city)
                            Toast.makeText(context,"Blood Requested Successfully",Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(context,"Blood Requested Successfully",Toast.LENGTH_LONG).show()
                            Log.e("ScheduleTime", "Error scheduling time: ${e.message}")

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(bottom = 30.dp,top = 20.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = dRed,
                        contentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(5.dp)

                ) {
                    Text(text = "Request", color = Color.White, fontSize = 22.sp)

                }


            }

        }


    }
}








@Preview(showBackground = true)
@Composable
fun Show() {
    val navController = rememberNavController()
    UrgentBloodScreen(navController = navController)

}