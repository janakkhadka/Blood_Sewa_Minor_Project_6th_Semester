package com.example.donation.moreItems


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.donation.BottomNavBar.CustomTopBar
import com.example.donation.BottomNavBar.TopBarTheme
import com.example.donation.ui.theme.dRed

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground =true )
@Composable
fun ViewEvents(){
    val navController = rememberNavController()
    var showCameraScreen by remember { mutableStateOf(false) }

    //camera screen set garna ko lagi
    if(showCameraScreen){
        ShowCamera(onBarcodeScanned = {
            showCameraScreen = false

        })
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Scaffold(
            topBar = {
                Column() {
                    TopBarTheme()
                    CustomTopBar(Icons.Default.ArrowBack, "", "", "Events", navController)
                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showCameraScreen = true},
                    backgroundColor = dRed
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White

                        )
                }
            }
        ) {

        }

    }


}
@Composable
fun ShowCamera(onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { show ->
            val previewView = PreviewView(show)
            val preview = androidx.camera.core.Preview.Builder().build()
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            // Use the BarcodeAnalyzer class here
            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(show),
                BarcodeAnalyzer(context) { barcodes ->
                    barcodes.firstOrNull()?.let { onBarcodeScanned(it) }
                }
            )

            runCatching {
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
                preview.setSurfaceProvider(previewView.surfaceProvider)
            }.onFailure { e ->
                Log.e("CameraScreen", "Error binding camera use cases: ${e.localizedMessage}")
            }

            previewView
        }
    )
}

class BarcodeAnalyzer(
    private val context: Context,
    private val onBarcodeDetected: (List<String>) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            scanner.process(
                InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            ).addOnSuccessListener { barcodes ->
                val barcodeValues = barcodes.mapNotNull { it.rawValue }
                if (barcodeValues.isNotEmpty()) {
                    onBarcodeDetected(barcodeValues)
                }
            }.addOnCompleteListener {
                imageProxy.close()
            }
        } ?: imageProxy.close()
    }
}
