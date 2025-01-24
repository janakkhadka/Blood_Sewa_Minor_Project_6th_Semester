package com.example.donation.ExtraItems

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.donation.R
import com.example.donation.ui.theme.dRed
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun EventViewExtended(
    navController : NavHostController
) {

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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        initiateScanner(instance) { scannedValue ->
                            value = scannedValue

                        }
                    },
                    backgroundColor = dRed
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = "Scan QR Code",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
        ) {padding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(start = 10.dp,end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start
            ){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                    Image(
                        painter = painterResource(R.drawable.donate),
                        contentDescription = "",
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }

//                Text(text = "Event Name : ${name}")
//                Text(text = "Organizer : ${organizer}")
//                Text(text = "Date : ${date}")
//                Text(text = "Collaboration with : ${collaborator}")
//                Text(text = "location : ${location}")
//                Text(text = "Description : ${description}")



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




