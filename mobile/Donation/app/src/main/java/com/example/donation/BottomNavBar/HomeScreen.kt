package com.example.donation.BottomNavBar

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.DataClasses.BloodRequest
import com.example.donation.DataClasses.SeeBloodRequest
import com.example.donation.DataClasses.UpcomingEvents
import com.example.donation.Navigation.Screens
import com.example.donation.R
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ViewModels.dummyEvent
import com.example.donation.ViewModels.dummyUrgentData
import com.example.donation.datastore.DataStoreManager
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.RedThemeTop
import com.example.donation.ui.theme.RedTop
import com.example.donation.ui.theme.blue
import com.example.donation.ui.theme.dRed
import java.time.LocalTime





@Composable
fun HomeScreen(navController : NavHostController,viewModel: SharedViewModel = viewModel()) {

//    LaunchedEffect(Unit) {
//        viewModel.fetchBloodRequests()
//    }
//    LaunchedEffect(Unit) {
//        viewModel.fetchUpcomingEventsList()
//    }
    val bloodRequests by viewModel.bloodRequests.collectAsState()
    val eventlists by viewModel.eventUpList.collectAsState()

    var showDialogBox by remember { mutableStateOf(false) }
    var eventDialog by remember { mutableStateOf(false) }
    var selectedPatient by remember { mutableStateOf<SeeBloodRequest?>(null) }
    var selectedEvent by remember { mutableStateOf<UpcomingEvents?>(null) }

    if (showDialogBox && selectedPatient != null) {
        Dialog(onDismissRequest = { showDialogBox = false }) {
            DialogBox(persons = selectedPatient!!)
        }
    }
    if (eventDialog && selectedEvent != null) {
        Dialog(onDismissRequest = { eventDialog = false }) {
            EventDialog(events = selectedEvent!!)
        }

    }


    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)



    val _username = dataStoreManager.getUserName
    val username by  dataStoreManager.getUserName.collectAsState(_username)





    val hour = LocalTime.now().hour
    var greetingText = when (hour) {
        in 1..12 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"


    }
    Column() {
        TopBarTheme()
        CustomTopBar(
            Icons.Default.Person,
            greetingText,
            "",
            "Blood Sewa",
            navController
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = " Urgent Requests",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(200.dp)
                        .shadow(elevation = 50.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            showDialogBox = true
                        }


                ) {
                    LazyRow {
                        items(bloodRequests) { person ->
                            PersonItem(person) {
                                selectedPatient = person
                                showDialogBox = true
                            }

                        }

                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "More Items",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
                // Inside the Box for "More Items"
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(200.dp)
                        .shadow(elevation = 50.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // First row of items
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconWithLabel(Icons.Default.Event, "My Events")
                            {
                                navController.navigate(Screens.MyEvents.route)

                            }
                            IconWithLabel(Icons.Default.PersonAddAlt1, "Search Donor")
                            {
                                navController.navigate(Screens.SearchDonors.route)
                            }
                            IconWithLabel(Icons.Default.LocalHospital, "Blood Banks")
                            {
                                navController.navigate(Screens.BloodBanks.route)
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        // Second row of items
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconWithLabel(Icons.Default.PlaylistAdd, "Request")
                            {
                                navController.navigate(Screens.RequestSection.route)
                            }
                            IconWithLabel(Icons.Default.EventAvailable, "All Events")
                            {
                                navController.navigate(Screens.eventViewExtended.route)
                            }
                            IconWithLabel(Icons.Default.Schedule, "Your Schedules")
                            {
                                navController.navigate(Screens.myBookings.route)
                            }
                        }
                    }
                }
                Text(
                    text = "Events Happening",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .height(200.dp)
                        .shadow(elevation = 50.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            eventDialog = true
                        }


                ) {

                    LazyRow {
                        items(eventlists) { eventItem ->
                            EventData(eventItem){
                                selectedEvent = eventItem
                                eventDialog = true

                            }

                        }


                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}





@Composable
fun EventData(events: UpcomingEvents,onClick: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
            .height(100.dp)
            .width(320.dp)
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable {
                onClick()

            },
        contentAlignment = Alignment.Center

    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ){
            Text(text = "Venue : ${events.location}",color = RedTop)
            Text(text = "Organized by : ${events.organizer}")
            Text(text = "Collaboration with : ${events.collabrator_name}")
            Text(text = "Date : ${events.date}")


        }




    }

}


@Composable
fun PersonItem(person: SeeBloodRequest, onClick : () -> Unit) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(320.dp)
            .padding(10.dp)
            .shadow(elevation = 180.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center

    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(dRed),
                contentAlignment = Alignment.Center
            ){
                Text(person.blood_group, color = Color.White, fontSize = 22.sp)

            }
            Spacer(modifier = Modifier.width(6.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = " ${person.patient_name}", fontSize = 22.sp)
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = ""
                    )
                    Text(text = person.location)
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Default.Place,
                        contentDescription = ""
                    )
                    Text(text = person.contact)
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = ""
                    )
                    Text(text = "2081-10-12")
                }


            }


        }



    }

}


@Composable
fun IconWithLabel(icon: ImageVector, label: String,onClick :() ->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Image(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(35.dp),
            colorFilter = ColorFilter.tint(RedThemeTop)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}








@Composable
fun DialogBox(persons :SeeBloodRequest){
    val context = LocalContext.current
    val contentToShare = "${persons.patient_name}\n${persons.contact}\n${persons.location}\n${persons.blood_group}"
    Box(
        modifier = Modifier.height(300.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.height(105.dp)
                    .fillMaxWidth()
                    .shadow(elevation = 20.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.donate),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                        .padding(top = 10.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 10.dp)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                    Text(text = "Contact Person : ${persons.patient_name}")
                    Text(text = "Phone : ${persons.contact}")
                    Text(text = "Hospital : ${persons.location}")
                    Text(text = "Case : ${persons.blood_group}")

                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 10.dp, end = 10.dp)
                ){
                    Button(
                        onClick = {
                            if(persons.contact.isNotEmpty()){
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("9865445343")
                                }
                                try {
                                    context.startActivity(intent)
                                }catch (e : Exception){
                                    Log.e("Err","${e.message}")
                                }
                            }else{
                                Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .shadow(elevation = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(DarkGreen)
                    ) {
                        Text(text ="Call")
                    }
                    Button(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT,contentToShare)

                            }
                            val chooserIntent = Intent.createChooser(shareIntent,"share via")
                            context.startActivity(chooserIntent)
                        },
                        modifier = Modifier
                            .shadow(elevation = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(blue)
                    ) {
                        Text(text ="Share")
                    }


                }
            }
        }

    }
}



@Composable
fun EventDialog(events : UpcomingEvents){
    val context = LocalContext.current
    val contentToShare = "${events.location}\n${events.date}\n${events.name}\n${events.collabrator_name}\n${events.organizer}\n${events.description}"
    Box(
        modifier = Modifier.height(300.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White),
        contentAlignment = Alignment.TopStart
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp,top =10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),

        ){
            Text(text ="Venue: ${events.location}")
            Text(text ="Organized by:${events.organizer}")
            Text(text ="date: ${events.date}")
            Text(text ="Collaboration with: ${events.collabrator_name}")
            Text(text ="Description: ${events.description}")
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Button(
                        onClick = {
//                            if(events..isNotEmpty()){
//                            val intent = Intent(Intent.ACTION_DIAL).apply {
//                                data = Uri.parse("9865445343")
//                                }
//                            try {
//                                context.startActivity(intent)
//                            }catch (e : Exception){
//                                Log.e("Err","${e.message}")
//                            }
//                        }else{
//                                Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show()
//                            }
                        },
                        modifier = Modifier
                            .shadow(elevation = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(DarkGreen)
                    ) {
                        Text(text = "Call")
                    }
                    Button(


                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT,contentToShare)

                            }
                            val chooserIntent = Intent.createChooser(shareIntent,"share via")
                            context.startActivity(chooserIntent)

                        },
                        modifier = Modifier
                            .shadow(elevation = 20.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(blue)
                    ) {
                        Text(text = "Share")
                    }
                }
            }




        }

    }

}

@Preview(showBackground = true)
@Composable
fun Review(){
    val navController = rememberNavController()
    HomeScreen(navController)
}
