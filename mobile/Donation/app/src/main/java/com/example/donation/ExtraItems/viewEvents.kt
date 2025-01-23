package com.example.donation.ExtraItems



import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.DataClasses.EventList
import com.example.donation.R
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewEvents(navController : NavHostController,viewModel: SharedViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.fetchEventsList()
    }

    val eventlists by viewModel.eventList.collectAsState()

    //scanner ko lagi
    val gmsScannerOptions = configureScannerOption()
    val instance = getBarcodeScannerInstance(gmsScannerOptions)
    var value by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Column {
                    TopBarTheme()
                    CustomTopBar(Icons.Default.ArrowBack, "", "", "Events", navController)
                }
           },
//            floatingActionButton = {
//                FloatingActionButton(
//                    onClick = {
//                        initiateScanner(instance){scannedValue ->
//                            value = scannedValue
//
//                        }
//                    },
//                    backgroundColor = dRed
//                ) {
//                    Icon(
//                        Icons.Default.QrCodeScanner,
//                        contentDescription = "Scan QR Code",
//                        modifier = Modifier.size(30.dp),
//                        tint = Color.White
//                    )
//                }
//            }
        ) {
            eventlists.forEach { list ->
                EventShow(list)

            }


        }
    }
}

private fun configureScannerOption(): GmsBarcodeScannerOptions {
    return GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC
        )
        .build()
}

@Composable
private fun getBarcodeScannerInstance(gmsBarcodeScannerOptions: GmsBarcodeScannerOptions): GmsBarcodeScanner {
    val context = LocalContext.current
    return GmsBarcodeScanning.getClient(context,gmsBarcodeScannerOptions)
}

private fun initiateScanner(gmsBarcodeScanner: GmsBarcodeScanner,onScanned : (String) -> Unit) {
    gmsBarcodeScanner.startScan()
        .addOnSuccessListener { barcode ->
            barcode.rawValue?.let { onScanned(it) }
            when (barcode.valueType) {
                Barcode.TYPE_URL -> {
                    Log.d(TAG, "initiateScanner: ${barcode.valueType}")
                }

                else -> {
                    Log.d(TAG, "initiateScanner: ${barcode.valueType}")
                }
            }

            Log.d(TAG, "initiateScanner: Display value ${barcode.displayValue}")
            Log.d(TAG, "initiateScanner: Display value ${barcode.format}")
        }
        .addOnCanceledListener {
            // cancell vako bela
        }
        .addOnFailureListener { e ->
            // exception
        }
}


@Composable
fun EventShow(data : EventList){
        Box(
            modifier = Modifier.fillMaxWidth()
                .shadow(elevation = 250.dp)
                .padding(top = 20.dp, start = 10.dp,end = 10.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(White),


        ){

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top =15.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(R.drawable.donate),
                    contentDescription = "",
                    modifier = Modifier.size(60.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text("Name : ${data.name}")
                    Text("Date : ${data.date}")
                    Text("Location : ${data.location}")

                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(DarkGreen),
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .width(60.dp)
                        .height(30.dp)
                ) {
                    Text(text = "Join", color = white, fontSize = 10.sp)
                }


            }

        }

    }







