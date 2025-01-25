package com.example.donation.ExtraItems

import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.ComposeCompilerApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.DataClasses.CreateEvent
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ViewModels.dummyEvent
import com.example.donation.ui.theme.RedThemeTop
import com.example.donation.ui.theme.dRed


@Composable
fun CreateEvents(navController : NavHostController,viewModel: SharedViewModel = viewModel()){
    var eventName by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var collaboration_with by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val context = LocalContext.current

    //dummy data
//    val hospitals = listOf("KMC Hospital","Civil Hospital","Bhaktapur Cancer Hospital")
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            TopBarTheme()
            CustomTopBar(Icons.Default.ArrowBack, "", "", "Create Events",navController)
//
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
                Spacer(modifier = Modifier.height(40.dp))
                OutlineTextField(eventName,"Event Name", onValueChange = {eventName = it})
                OutlineTextField(eventTime,"Event Date", onValueChange = {eventTime = it})
                OutlineTextField(collaboration_with,"Collaboration With", onValueChange = {collaboration_with = it})
                OutlineTextField(venue,"venue", onValueChange = {venue = it})
                OutlineTextField(desc,"Description", onValueChange = {desc = it})
                Button(
                    onClick = {


                        try {
                            viewModel.createEvent(eventName, desc,venue,collaboration_with,eventTime)
                            Toast.makeText(context,"Event Created Successfully", Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Log.e("ScheduleTime", "Error scheduling time: ${e.message}")

                        }

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
fun OutlineTextField( text : String,label : String,onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange ,
        label = {Text(label)},
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(.8f)

    )
}



@Preview(showBackground = true)
@Composable
fun showData(){
    val navController = rememberNavController()
    CreateEvents(navController)
}
