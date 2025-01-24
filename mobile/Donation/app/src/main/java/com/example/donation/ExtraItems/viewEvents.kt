package com.example.donation.ExtraItems



import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import com.example.donation.Navigation.Screens
import com.example.donation.R
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ViewModels.dummyEvent
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.blue
import com.example.donation.ui.theme.dRed
import com.example.donation.ui.theme.white
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewEvents(navController: NavHostController, viewModel: SharedViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchEventsList()
    }

    val eventlists by viewModel.eventList.collectAsState()

    // Scanner configuration
    val gmsScannerOptions = configureScannerOption()
    val instance = getBarcodeScannerInstance(gmsScannerOptions)
    var value by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TopBar
            Column {
                TopBarTheme()
                CustomTopBar(Icons.Default.ArrowBack, "", "", "Events", navController)
            }

            // Event List
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(eventlists) { lists ->
                    EventShow(lists)
                }
            }
        }

        // Floating Action Button at the bottom right
        FloatingActionButton(
            onClick = {
                initiateScanner(instance) { scannedValue ->
                    value = scannedValue
                }
            },
            backgroundColor = dRed,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                Icons.Default.QrCodeScanner,
                contentDescription = "Scan QR Code",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
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
fun EventShow(data: EventList) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val contentToShare = " Event Name:${data.name}\n Organized By :${data.organizer}\n Collaboration_with${data.collabrator_name}\n Date :${data.date}\n Description :${data.description}"
    var status  by remember { mutableStateOf(false) }
    var (text,color) = if(status) "Joined" to DarkGreen else "Not Joined" to Color.Red

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 100.dp)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(White)
            .clickable {
                showDialog = true
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.donate),
                contentDescription = "",
                modifier = Modifier.size(60.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Name: ${data.name}")
                Text("Date: ${data.date}")
                Text("Location: ${data.location}")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    Text(text = "Status :")
                    Text(text = text, color = color )
                }
            }

        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Event Details")
            },
            text = {
                Column {
                    Text("Name: ${data.name}")
                    Text("Date: ${data.date}")
                    Text("Location: ${data.location}")
                    Text("Collaboration With: ${data.collabrator_name}")
                    Text("Description : ${data.description}")
                }
            },
            confirmButton = {
                Button(onClick = {
                    status = true
                    showDialog = false

                }) {
                    Text("Join")
                }
            },
            dismissButton = {
                Button(onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT,contentToShare)

                    }
                    val chooserIntent = Intent.createChooser(shareIntent,"share via")
                    context.startActivity(chooserIntent)


                },
                    colors = ButtonDefaults.buttonColors(DarkGreen),
                ) {
                    Text("Share", color = White)
                }
            }
        )
    }
}
