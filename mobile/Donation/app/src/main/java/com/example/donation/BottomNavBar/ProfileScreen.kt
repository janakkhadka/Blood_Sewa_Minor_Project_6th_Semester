package com.example.donation.BottomNavBar


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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Countertops
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.DataClasses.EventDonationHistory
import com.example.donation.DataClasses.MyEventHistory
import com.example.donation.Navigation.Screens
import com.example.donation.R
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.dRed






data class BadgeDetails(
    val imageRes: Int,
    val badgeText: String,
    val rangeMin: Int,
    val rangeMax: Int
)
data class Report(
    val header : String,
    val value : String
)
@Composable
fun ProfileScreen(navController : NavHostController,viewModel: SharedViewModel = viewModel()) {


    LaunchedEffect(Unit) {
        viewModel.fetchEventHistory()
    }


    //badge ko lagi
    val donationCount by remember { mutableIntStateOf(8) }
    val badgeDetails = when (donationCount) {
        in 0..10 -> BadgeDetails(R.drawable.bronze, "Bronze", 0, 10)
        in 11..20 -> BadgeDetails(R.drawable.silver, "Silver", 11, 20)
        in 21..30 -> BadgeDetails(R.drawable.platinum, "Platinum", 21, 30)
        in 31..40 -> BadgeDetails(R.drawable.diamond, "Diamond", 31, 40)
        else -> BadgeDetails(R.drawable.heroic, "Heroic", 41, 50)
    }

    val (imageRes, badgeText, rangeMin, rangeMax) = badgeDetails

    val progress = if(rangeMin == rangeMax) 1f
    else (donationCount-rangeMin).toFloat()/(rangeMax-rangeMin)

    val report = listOf(
        Report("Blood Pressure","120/80 mmHg"),
        Report("Pulse rate","72 bpm"),
        Report("Body Temperature","98.6°F"),
        Report("Hemoglobin","17.2 g/dL"),
        Report("Blood Sugar","85 mg/dL")


    )

    val dummyHistroy = listOf(
        EventDonationHistory(
            even_name = "campaign",
            date = "jan-02,2024",
            activity = "donated"
        )
    )


    Scaffold(
        topBar = {
            Column() {
                TopBarTheme()
                CustomTopBar(Icons.Default.ArrowBack, "", "", "Profile Section", navController)
            }
        },


    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp,top = 10.dp),

                    ) {
                    Text(text = "User Information", fontSize = 20.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            Icons.Default.PersonOutline,
                            contentDescription = "",
                            modifier = Modifier.size(60.dp)
                        )
                        Column() {
                            Text(text = "Kiran Acharya", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "kiran.211720@ncit.edu.np", fontSize = 16.sp)
                        }
                    }

                    PDetails(Icons.Default.CalendarMonth,"Date of Birth:","2003")
                    PDetails(Icons.Default.CalendarMonth,"Gender:","Male")
                    PDetails(Icons.Default.Phone,"Phone:","+977-98675445343")
                    PDetails(Icons.Default.Home,"Address:","Kalikot,Karnali Province")

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screens.updateProfile.route)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(dRed),


                            ) {
                            Text(text = "Update Profile", fontSize = 18.sp)
                        }
                    }
                }
            }


            //donation details
            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.TopStart,

                ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp,top = 10.dp)



                ) {
                    Text(text = "Donation Profile", fontWeight = FontWeight.Bold, color = dRed, fontSize = 22.sp)
                    Text(text = "Your donation have made a difference!", fontSize = 14.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            Details(Icons.Default.LocalFlorist, "Current Badge:", badgeText)
                            Row(
                                modifier = Modifier.padding(start = 10.dp, top = 10.dp),

                                ) {
                                Text(text = "Progress in ${badgeText}")
                                Spacer(modifier = Modifier.width(30.dp))
                                Text(text = "${(progress * 100).toInt()}%%", fontWeight = FontWeight.Bold)

                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            LinearProgressIndicator(
                                progress = progress.coerceIn(0f,1f),
                                modifier = Modifier
                                    .height(20.dp)
                                    .shadow( elevation = 20.dp)
                                    .padding(start = 10.dp)
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                color = Color.Green,
                                trackColor = dRed


                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Details(Icons.Default.Bloodtype, "Blood Type", "AB-")
                        }
                        Image(
                            painter = painterResource(imageRes),
                            contentDescription = "",
                            modifier = Modifier.size(100.dp)

                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }

            //donation information
            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.TopStart,

                ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)


                ) {
                    Text(text = " Donation Information", fontWeight = FontWeight.Bold, color = dRed, fontSize = 22.sp)

                    Details(Icons.Default.Countertops, "Donation Count:", "21")
                    Details(Icons.Default.CalendarMonth, "Last Donation Date:", "December 1, 2024")
                    Details(Icons.Default.LocationOn, "Last Donation Event:", "KMC Hospital")

                    Text(
                        text = " Your are currently elligible to Donate Blood",
                        modifier = Modifier.padding(bottom = 10.dp),
                        color = dRed
                    )
                    //last donation blood information

                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)


                        ) {
                            Text(text = " Last Donation Blood Information", fontWeight = FontWeight.Bold, color = dRed, fontSize = 16.sp)
                            report.forEach{report ->
                                DonationReport(report)

                            }


                            //download report
                            Text(
                                text = "Download Report",
                                textDecoration = TextDecoration.Underline,
                                color = Color.Blue,
                                modifier = Modifier.padding(bottom = 10.dp)
                                    .clickable {

                                    }
                            )

                        }

                    }



                }




            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp, bottom = 150.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(elevation = 50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White),
                contentAlignment = Alignment.TopStart,

                ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 10.dp,top = 10.dp,end = 10.dp)
                ) {

                        Text(text = "Donation Activity", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = dRed)



                    DonationActivityHeading()
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                }

                    }
                    Spacer(modifier =  Modifier.height(10.dp))

                }

            }
        }


    }





@Composable
fun Details(
    Icons : ImageVector,
    text : String,
    value : String
) {
    Row(
        modifier = Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            Icons,
            contentDescription = "",
            colorFilter = ColorFilter.tint(dRed)
        )
        Row(
            modifier = Modifier.padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = text, color = dRed)
            Text(text = value, color = dRed, fontWeight = FontWeight.Bold)

        }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
    }
}

    @Composable
    fun PDetails(
        Icons : ImageVector,
        text : String,
        value : String
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                Icons,
                contentDescription = ""
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(text = text)
                Text(text = value)

            }
        }


    }

@Composable
fun DonationActivityHeading(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Date", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = dRed)
        Text(text = "Event",fontSize = 16.sp,fontWeight = FontWeight.Bold, color = dRed)
        Text(text = "Activity",fontSize = 16.sp,fontWeight = FontWeight.Bold, color = dRed)

    }

}
@Composable
fun DonationActivity(data: MyEventHistory){

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = data.joined_on, fontSize = 14.sp, color = dRed)
        Text(text = data.event_name,fontSize = 14.sp, color = dRed)
       // Text(text = data.Donated,fontSize = 14.sp, color = dRed)

    }

}

@Composable
fun DonationReport(
    report : Report
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Image(
            imageVector = Icons.Default.Star,
            contentDescription = "",
            colorFilter = ColorFilter.tint(dRed)
        )
        Text(text = report.header, color = dRed, fontSize = 14.sp)
        Text(text = report.value, color = dRed, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }

}


@Preview(showBackground = true)
@Composable
fun show(){
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}





