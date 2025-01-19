package com.example.donation.MoreItems



import android.content.Intent
import android.graphics.Paint.Align
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambdaN
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
import com.example.donation.backend.searchDonor.SearchDonor
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.lightGreen

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

    //dummy data values
    val donorDatas = listOf(
        DonorsList("A+","Kiran Acharya","9865445343","Kalikot","Karnali","23"),
        DonorsList("AB+","Kishor Acharya","9823366044","Kathmandu","Bagmati","22"),
        DonorsList("O+","Janak Khadka","9847984933","kailali","Sudhurpaschim","33"),
        DonorsList("AB-","Bishal Parajuli","999999999","Kavre","Bagmati","22"),
        DonorsList("A+","Neymar junior","9090909090","Kapilvastu","Lumbini","19")

    )


    //filter apply gareko yesma
    val filterData = if(selectedBloodType.isEmpty()){
        donorDatas
    }else{
        donorDatas.filter { it.blood_group == selectedBloodType }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarTheme()
        CustomTopBar(img = Icons.Default.ArrowBack, greetingText = "", name = "", text = "Search Donors",navController)
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
fun PrevieW(){
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
