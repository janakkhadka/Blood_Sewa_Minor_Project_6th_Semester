import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage



class BarCodeAnalyser(
    private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image
        if (mediaImage != null) {
            val imageToProcess = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

            val barcodeScanner = BarcodeScanning.getClient(BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build())

            barcodeScanner.process(imageToProcess)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        onBarcodeDetected(barcodes)
                    } else {
                        Log.d("BarcodeAnalyser", "No barcodes detected")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("BarcodeAnalyser", "Barcode detection failed: ${exception.message}")
                }
                .addOnCompleteListener {
                    image.close()
                }
        }
    }
}
