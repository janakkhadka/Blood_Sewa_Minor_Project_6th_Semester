package com.example.donation.ExtraItems

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.DataClasses.CreateEvent
import com.example.donation.DataClasses.OrganizationInventory
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ViewModels.dummyEvent
import com.example.donation.ui.theme.RedThemeTop
import com.example.donation.ui.theme.dRed


@SuppressLint("DefaultLocale")
@Composable
fun CreateEvents(navController : NavHostController,viewModel: SharedViewModel = viewModel()){
    var eventName by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var volunteerReq by remember { mutableStateOf(0) }
    var hospitalExpanded by remember { mutableStateOf(false) }
    var hospitalSelected by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    //time picker ko lagi
    val calendar = Calendar.getInstance()

    var selectedTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }


    //timer for start
    val timePickerDialogStart = TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    //timer for end

    val timePickerDialogEnd = TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            endTime = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )


    LaunchedEffect(Unit) {
        viewModel.fetchOrganizations()
    }
    val organizationList by viewModel.organizations.collectAsState()



    Box(modifier = Modifier.fillMaxSize()) {
        Column(
        ) {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "Create Events",navController)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 50.dp)
            ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                OutlineTextField(eventName,"Event Name", onValueChange = {eventName = it})
                OutlineTextField(eventTime,"Event Date", onValueChange = {eventTime = it})

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                    OutlinedTextField(
                        value = hospitalSelected,
                        onValueChange = { hospitalSelected = it },
                        readOnly = true,
                        label = { Text("Select Blood Bank") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (hospitalExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = if (hospitalExpanded) "Collapse Dropdown" else "Expand Dropdown",
                                modifier = Modifier.clickable { hospitalExpanded = !hospitalExpanded }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(0.9f)
                            .align(Alignment.TopCenter)
                    )

                    Box(modifier = Modifier.matchParentSize().clickable { hospitalExpanded = true }.background(Color.Transparent))

                    DropdownMenu(
                        expanded = hospitalExpanded,
                        onDismissRequest = { hospitalExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        organizationList.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    hospitalSelected = option
                                    hospitalExpanded = false
                                })
                        }
                    }
                }
                OutlineTextField(venue,"venue", onValueChange = {venue = it})


                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                    OutlinedTextField(
                        value = selectedTime,
                        onValueChange = { selectedTime = it },
                        label = { Text("Start Time") },
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .clickable { timePickerDialogStart.show() },
                        readOnly = true
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { timePickerDialogStart.show() }.background(Color.Transparent))
                }

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("end Time") },
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .clickable { timePickerDialogEnd.show() },
                        readOnly = true
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { timePickerDialogEnd.show() }.background(Color.Transparent))
                }



                OutlinedTextField(
                        value = volunteerReq.toString(),
                        onValueChange = { newText ->
                            volunteerReq = newText.toIntOrNull() ?: 0
                        },
                        label = { Text("Volunteer") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(.9f)
                    )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(.9f)
                        .height(150.dp)
                )




                Button(
                    onClick = {
                        try {
                            viewModel.createEvent(eventName, desc,venue,hospitalSelected,eventTime,selectedTime,endTime,volunteerReq)
                            Toast.makeText(context,"Event Created Successfully", Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Log.e("ScheduleTime", "Error scheduling time: ${e.message}")

                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth(.9f)
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
fun OutlineTextField( text : String,label : String,onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange ,
        label = {Text(label)},
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(.9f)

    )
}



@Preview(showBackground = true)
@Composable
fun showData(){
    val navController = rememberNavController()
    CreateEvents(navController)
}
