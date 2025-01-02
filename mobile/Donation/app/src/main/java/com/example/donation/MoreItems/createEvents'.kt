package com.example.donation.moreItems

import android.graphics.Paint.Align
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.RedThemeTop
import com.example.donation.ui.theme.blue
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.lightGreen

@Preview(showBackground = true)
@Composable
fun CreateEvents(){
    val navController = rememberNavController()
    var eventName by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var collaboration_with by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }

    //dummy data
    val hospitals = listOf("KMC Hospital","Civil Hospital","Bhaktapur Cancer Hospital")
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "Create Events",navController)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//                    .clip(shape = RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp))
//                    .background(dRed)
//            ) {}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(
                        listOf(
                            Color.White,
                            dRed
                        )

                    )),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlineTextField(eventName,"Event Name",{eventName = it})
                OutlineTextField(eventTime,"Event Date",{eventTime = it})
                OutlineTextField(collaboration_with,"Collaboration With") { }
                OutlineTextField(startTime,"Start Time") {}
                OutlineTextField(endTime,"End Time") { }
                OutlineTextField(collaboration_with,"Collaboration With") { }
                OutlineTextField(venue,"venue") { }
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(50.dp)
                        .shadow(elevation = 20.dp),
                    colors = ButtonDefaults.buttonColors(RedThemeTop),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "CREATE", fontSize = 22.sp)
                }
            }


        }





    }
}

@Composable
fun OutlineTextField(  text : String,label : String,onValueChange : (String) -> Unit){
    OutlinedTextField(
        value = text,
        onValueChange = {onValueChange},
        label = {Text(label)},
        shape = RoundedCornerShape(10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialExample(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column {
        TimePicker(
            state = timePickerState,
        )
        Button(onClick = onDismiss) {
            Text("Dismiss picker")
        }
        Button(onClick = onConfirm) {
            Text("Confirm selection")
        }
    }
}

