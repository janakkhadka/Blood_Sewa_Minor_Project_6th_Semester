package com.example.donation.BottomNavBar


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.donation.ui.theme.dRed


@Composable
fun UpdateProfile() {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Alertdialog(showDialog = showDialog, onDismiss = { showDialog = false })
    }

    val navController = rememberNavController()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "Update Profile", navController)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth(.8f),
                    value = email,
                    onValueChange = { email = it },
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Email") },
                    leadingIcon =
                    {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = ""
                        )
                    }
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.8f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = ""
                        )
                    }

                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(.8f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = ""
                        )
                    }
                )
                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(.8f)
                        .padding(bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = dRed,
                        contentColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Update", color = Color.White, fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun Alertdialog(showDialog: Boolean, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmation") },
        text = { Text("Are you sure you want to update?") },
        confirmButton = {
            Button(
                onClick = {

                    onDismiss()//yes vayo vhane k garne yeta
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss //no vayo vhane k garne yeta
            ) {
                Text("No")
            }
        }
    )
}
