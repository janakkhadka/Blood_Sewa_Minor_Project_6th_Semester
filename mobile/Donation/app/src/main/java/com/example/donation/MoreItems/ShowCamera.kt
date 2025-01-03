package com.example.donation.moreItems

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


//@Composable
//fun ShowCamera(onBarcodeScanned: (String) -> Unit) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
//
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { show ->
//            val previewView = PreviewView(show)
//            val preview = Preview.Builder().build()
//            val cameraSelector = CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build()
//
////image analysis graxa yesle
//            val imageAnalysis = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//
//            // barcode analyzer set grana ko lagi
//            imageAnalysis.setAnalyzer(
//                ContextCompat.getMainExecutor(show),
//                BarcodeAnalyzer(show) { barcodes ->
//                    barcodes.firstOrNull()?.let { onBarcodeScanned(it) }
//                }
//            )
//
//            runCatching {
//                val cameraProvider = cameraProviderFuture.get()
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(
//                    lifecycleOwner,
//                    cameraSelector,
//                    preview,
//                    imageAnalysis
//                )
//                preview.setSurfaceProvider(previewView.surfaceProvider)
//            }.onFailure { e ->
//                Log.e("CameraScreen", "Error binding camera use cases: ${e.localizedMessage}")
//            }
//
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
//    private val options = BarcodeScannerOptions.Builder()
//        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
//        .build()
//
//    private val scanner = BarcodeScanning.getClient(options)
//
//    @SuppressLint("UnsafeOptInUsageError")
//    override fun analyze(imageProxy: ImageProxy) {
//        imageProxy.image?.let { image ->
//            scanner.process(
//                InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
//            ).addOnSuccessListener { barcodes ->
//                val barcodeValues = barcodes.mapNotNull { it.rawValue }
//                if (barcodeValues.isNotEmpty()) {
//                    onBarcodeDetected(barcodeValues)
//                }
//            }.addOnCompleteListener {
//                imageProxy.close()
//            }
//        }
//    }
//}
