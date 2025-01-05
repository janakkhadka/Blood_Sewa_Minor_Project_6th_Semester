package com.example.donation.moreItems



import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViewEvents() {
    val navController = rememberNavController()

    //scanner ko lagi
    val gmsScannerOptions = configureScannerOption()
    val instance = getBarcodeScannerInstance(gmsScannerOptions)

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
                        initiateScanner(instance)
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
        ) {

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

private fun initiateScanner(gmsBarcodeScanner: GmsBarcodeScanner) {
    gmsBarcodeScanner.startScan()
        .addOnSuccessListener { barcode ->
            val result = barcode.rawValue
            Log.d(TAG, "initiateScanner: $result")

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




