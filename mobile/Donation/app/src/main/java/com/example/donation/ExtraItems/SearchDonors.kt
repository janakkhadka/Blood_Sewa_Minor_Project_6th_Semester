package com.example.donation.ExtraItems



import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.backend.RegViewModel
import com.example.donation.backend.searchDonor.SearchDonor
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.dRed

//dummy data for searching
data class DonorsList(
    val blood_group : String,
    val name : String,
    val phone_number : String,
    val district : String,
    val province : String,
    val age : String
)


@Composable
fun SearchDonors(navController : NavHostController) {
    val scrollState = rememberScrollState()
    var selectedBloodType by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var province by remember { mutableStateOf("") }
    var Districtexpanded by remember { mutableStateOf(false) }
    var district by remember { mutableStateOf("") }
    var currentDistricts by remember { mutableStateOf(listOf<String>()) }

    //dummy data values
    val donorDatas = listOf(
        DonorsList("A+", "Kiran Acharya", "9865445343", "Kalikot", "Karnali", "23"),
        DonorsList("AB+", "Kishor Acharya", "9823366044", "Kathmandu", "Bagmati", "22"),
        DonorsList("O+", "Janak Khadka", "9847984933", "kailali", "Sudhurpaschim", "33"),
        DonorsList("AB-", "Bishal Parajuli", "999999999", "Kavre", "Bagmati", "22"),
        DonorsList("A+", "Neymar junior", "9090909090", "Kapilvastu", "Lumbini", "19")

    )

    //district rw province
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

    val filterData = filterDonors(
        donors = donorDatas,
        selectedBloodGroup = selectedBloodType,
        selectedProvince = province,
        selectedDistrict = district
    )


    Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarTheme()
            CustomTopBar(
                img = Icons.Default.ArrowBack,
                greetingText = "",
                name = "",
                text = "Search Donors",
                navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val bloodTypes = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
                bloodTypes.forEach { bloodType ->
                    RowSearchBlood(
                        text = bloodType,
                        isSelected = selectedBloodType == bloodType,
                        onClick = { selectedBloodType = bloodType }
                    )
                }



        }

        Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),

            ){

                Box(
                    modifier = Modifier.fillMaxWidth(.9f),
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
                            .padding(start = 20.dp)
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
                    modifier = Modifier.fillMaxWidth()
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
                        .fillMaxWidth()
                        .padding(start = 20.dp)
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
                        modifier = Modifier.fillMaxWidth(.9f)
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

            }
            Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filterData) { donors ->
                    ShowDataItems(donors)


                }

            }
        }
    }
}



@Composable
fun RowSearchBlood(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(top = 10.dp)
            .clip(CircleShape)
            .background(if (isSelected) dRed else Color.LightGray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun PReVuew(){
    val navController = rememberNavController()
    SearchDonors(navController)

}

//@Preview(showBackground = true)
@Composable
fun ShowDataItems(donor : DonorsList){
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
                    text = donor.blood_group,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = " Name : ${donor.name}")
                Text(text = " Age : ${donor.age}")
                Text(text = " Phone Number : ${donor.phone_number}")
                Text(text = " Address : ${donor.district}, ${donor.province}")
                    Button(
                        onClick = {
                            val number = donor.phone_number
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

//filter garna laii use hune function
fun filterDonors(
    donors: List<DonorsList>,
    selectedBloodGroup: String,
    selectedProvince: String,
    selectedDistrict: String
): List<DonorsList> {
    return donors.filter { donor ->
        (selectedBloodGroup.isEmpty() || donor.blood_group == selectedBloodGroup) &&
                (selectedProvince.isEmpty() || donor.province == selectedProvince) &&
                (selectedDistrict.isEmpty() || donor.district == selectedDistrict)
    }
}

