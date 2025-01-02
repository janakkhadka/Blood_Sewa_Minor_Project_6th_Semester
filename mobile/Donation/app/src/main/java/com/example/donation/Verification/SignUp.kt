package com.example.donation.Verification


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.Navigation.Screens
import com.example.donation.backend.UserRegistration
import com.example.donation.backend.registration.Registration
import com.example.donation.ui.theme.dRed
import kotlinx.coroutines.launch


@Composable
fun SignUp(navController : NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }
    if (loading) {
        CircularProgressIndicator()
    }

    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var isChecked by remember{ mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var Bloodexpanded by remember { mutableStateOf(false) }
    var Bloodselected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    var Districtexpanded by remember { mutableStateOf(false) }
    var Districtselected by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var currentDistricts by remember { mutableStateOf(listOf<String>()) }



    //button ko color ko lagi
    val colors = if (isChecked) {
        androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = dRed,
            contentColor = Color.White
        )
    } else {
        androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.Black
        )
    }


    val scrollState = rememberScrollState()
    var selectedProvice by remember{ mutableStateOf("") }
    val bloodOptions = listOf("A+", "B+", "A-", "B-", "O+", "O-", "AB+", "AB-")
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


    TopBarTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 200.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "SIGN UP",
            modifier = Modifier.padding(top = 80.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = username,
            onValueChange = { username = it },
            label = { Text("Full Name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account Circle Icon"
                )
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            OutlinedTextField(
                value = Bloodselected,
                onValueChange = { },
                readOnly = true,
                label = { Text("Select Blood Group") },
                trailingIcon = {
                    Icon(
                        imageVector = if (Bloodexpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (Bloodexpanded) "Collapse Dropdown" else "Expand Dropdown",
                        modifier = Modifier.clickable { Bloodexpanded = !Bloodexpanded }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { Bloodexpanded = true }
                    .background(Color.Transparent)
            )
        }

        DropdownMenu(
            expanded = Bloodexpanded,
            onDismissRequest = { Bloodexpanded = false },
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            bloodOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        Bloodselected = option
                        Bloodexpanded = false
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(.9f)
        ) {

            OutlinedTextField(
                value = selected,
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
                        selected = option
                        expanded = false

                        currentDistricts = when (selected) {
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
                value = Districtselected,
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
                        Districtselected = option
                        Districtexpanded = false
                    }
                )
            }
        }




        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            leadingIcon = {
                Text(text = "+977")
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = dob,
            onValueChange = { dob = it },
            label = { Text("DOB") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Account Circle Icon"
                )
            }
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Account Circle Icon"
                )
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(.9f),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "show password"
                    )

                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Account Circle Icon"
                )
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked =isChecked ,
                onCheckedChange ={ isChecked = it} )
            Text(text = " I agree to all the terms and conditions.")
        }


        Button(
            onClick = {

                if (username.isBlank() || email.isBlank() || phoneNumber.isBlank() ||
                    Bloodselected.isBlank() || Districtselected.isBlank() ||
                    selected.isBlank() || dob.isBlank() || password.isBlank() || !isChecked) {
                    Toast.makeText(context, "Please fill in all fields and agree to the terms.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(context, "Invalid email format.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (password.length < 6) {
                    Toast.makeText(context, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val registrationData = Registration(
                    email = email,
                    name = username,
                    phone_number = phoneNumber,
                    blood_group = Bloodselected,
                    district = Districtselected,
                    province = selected,
                    password = password,
                    dOB = dob

                )

                loading = true
                scope.launch {
                    try {
                        val response = UserRegistration.authService.registerUser(registrationData)
                        loading = false
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.Login.route)
                        } else {
                            Toast.makeText(context, "Registration failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        loading = false
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors =colors,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .height(40.dp),
            shape = RoundedCornerShape(8.dp)


        ) {
            Text(text = "SIGN UP", color = Color.White, fontSize = 18.sp)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = "Already have account?")
            Text(
                text = "Login",
                color = dRed,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screens.Login.route)

                    }
            )
        }


    }
}







