//package com.example.donation.moreItems
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.util.Log
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageProxy
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import androidx.navigation.NavHostController
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions
//import com.google.mlkit.vision.barcode.BarcodeScanning
//import com.google.mlkit.vision.barcode.common.Barcode
//import com.google.mlkit.vision.common.InputImage
//
//
//@Composable
//fun ShowCamera(navController : NavHostController, onBarcodeScanned: (String) -> Unit) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
//
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { show ->
//            val previewView = PreviewView(show)
//            cameraProviderFuture.addListener({
//                val cameraProvider = cameraProviderFuture.get()
//                val preview = androidx.camera.core.Preview.Builder().build()
//                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//                val imageAnalysis = ImageAnalysis.Builder()
//                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                    .build()
//
//                imageAnalysis.setAnalyzer(
//                    ContextCompat.getMainExecutor(context),
//                    BarcodeAnalyzer(context) { barcodes ->
//                        barcodes.firstOrNull()?.let { onBarcodeScanned(it) }
//                    }
//                )
//
//                try {
//                    cameraProvider.unbindAll()
//                    cameraProvider.bindToLifecycle(
//                        lifecycleOwner,
//                        cameraSelector,
//                        preview,
//                        imageAnalysis
//                    )
//                    preview.setSurfaceProvider(previewView.surfaceProvider)
//                } catch (e: Exception) {
//                    Log.e("ShowCamera", "Error binding camera use cases", e)
//                }
//            }, ContextCompat.getMainExecutor(context))
//            previewView
//        }
//    )
//}
//
//class BarcodeAnalyzer(
//    private val context: Context,
//    private val onBarcodeDetected: (List<String>) -> Unit
//) : ImageAnalysis.Analyzer {
//
//    private val scanner = BarcodeScanning.getClient(
//        BarcodeScannerOptions.Builder()
//            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
//            .build()
//    )
//
//    @SuppressLint("UnsafeOptInUsageError")
//    override fun analyze(imageProxy: ImageProxy) {
//        imageProxy.image?.let { image ->
//            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
//            scanner.process(inputImage)
//                .addOnSuccessListener { barcodes ->
//                    val barcodeValues = barcodes.mapNotNull { it.rawValue }
//                    if (barcodeValues.isNotEmpty()) {
//                        onBarcodeDetected(barcodeValues)
//                    }
//                }
//                .addOnFailureListener { e ->
//                    Log.e("BarcodeAnalyzer", "Error processing barcode", e)
//                }
//                .addOnCompleteListener {
//                    imageProxy.close()
//                }
//        } ?: imageProxy.close()
//    }
//}
