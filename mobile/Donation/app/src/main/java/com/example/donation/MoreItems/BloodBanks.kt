package com.example.donation.MoreItems

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme


@Composable
fun BloodBanks(navController: NavHostController) {
    var blood_selected by remember { mutableStateOf("") }
    var blood_expended by remember { mutableStateOf(false) }
    val blood_banks = listOf(
        "KMC Hospital",
        "Civil Hospital",
        "Bhaktapur Cancer Hospital"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name = "", text = "Blood Banks", navController)

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedTextField(
                value = blood_selected,
                onValueChange = { blood_selected = it },
                readOnly = true,
                label = { Text("Select Blood Bank") },
                trailingIcon = {
                    Icon(
                        imageVector = if (blood_expended) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (blood_expended) "Collapse Dropdown" else "Expand Dropdown",
                        modifier = Modifier.clickable { blood_expended = !blood_expended }
                    )
                },
                modifier = Modifier.fillMaxWidth(.9f)
                    .align(Alignment.TopCenter)
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { blood_expended = true }
                    .background(Color.Transparent)
            )
            DropdownMenu(
                expanded = blood_expended,
                onDismissRequest = { blood_expended = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                blood_banks.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            blood_selected = option
                            blood_expended = false
                        }
                    )
                }
            }

        }




    }
}

@Preview(showBackground = true)
@Composable
fun PShow(){
    val navController = rememberNavController()
    BloodBanks(navController)
}
