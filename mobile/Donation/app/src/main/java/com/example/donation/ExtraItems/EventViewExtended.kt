package com.example.donation.ExtraItems

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.donation.DataClasses.EventList
import com.example.donation.DataClasses.UpcomingEvents
import com.example.donation.Navigation.Screens
import com.example.donation.R
import com.example.donation.ViewModels.SharedViewModel
import com.example.donation.ui.theme.DarkGreen
import com.example.donation.ui.theme.RedTop
import com.example.donation.ui.theme.dRed
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun EventViewExtended(navController: NavHostController,viewModel: SharedViewModel = viewModel()) {
    val gmsScannerOptions = configureScannerOption()
    val instance = getBarcodeScannerInstance(gmsScannerOptions)
    var value by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchUpcomingEventsList()
    }
    val eventlists by viewModel.eventUpList.collectAsState()

    // Tab state
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Today's Events", "Upcoming Events")

    var isFabExpanded by remember { mutableStateOf(false) }
    val fabTranslation by animateDpAsState(
        targetValue = if (isFabExpanded) 80.dp else 0.dp
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Top bar
            Column {
                TopBarTheme()
                CustomTopBar(Icons.Default.ArrowBack, "", "", "Events", navController)
            }

            // Tabs
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colors.error
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    androidx.compose.material3.Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            androidx.compose.material3.Text(
                                title,
                                color = if (selectedTabIndex == index) dRed
                                else MaterialTheme.colors.onBackground
                            )
                        }
                    )
                }
            }

            // Tab content
            when (selectedTabIndex) {
                0 -> TodayEventsContent()
                1 -> LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(eventlists){requests ->
                        EventShow(requests)

                    }

                }
            }


        }


        FloatingActionButton(
            onClick = { isFabExpanded = !isFabExpanded },
            backgroundColor = DarkGreen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (isFabExpanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                contentDescription = "Toggle FABs",
                tint = Color.White
            )
        }


        if (isFabExpanded) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.createEvents.route)
                },
                backgroundColor = dRed,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp + fabTranslation, end = 16.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create Event",
                    tint = Color.White
                )
            }


            FloatingActionButton(
                onClick = {
                    initiateScanner(instance) { scannedValue ->
                        value = scannedValue
                    }
                },
                backgroundColor = dRed,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp + (fabTranslation * 2), end = 16.dp)
            ) {
                Icon(
                    Icons.Default.QrCodeScanner,
                    contentDescription = "Scan QR Code",
                    tint = Color.White
                )
            }

        }
    }

    
}

@Composable
fun TodayEventsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Today's Events", style = MaterialTheme.typography.h6)

    }
}

@Composable
fun EventShow(data: UpcomingEvents) {
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
    return GmsBarcodeScanning.getClient(context, gmsBarcodeScannerOptions)
}

private fun initiateScanner(
    gmsBarcodeScanner: GmsBarcodeScanner,
    onScanned: (String) -> Unit
) {
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
            Log.d(TAG, "Scanner canceled")
        }
        .addOnFailureListener { e ->
            Log.e(TAG, "Scanner failed", e)
        }
}
