package com.example.donation.moreItems

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@Composable
fun ShowCamera(navController: NavHostController){
    val context = LocalContext.current
    val cameraProvide = ProcessCameraProvider.getInstance(context)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { show ->
            val previewView  = PreviewView(show)
            val cameraProvider = cameraProvide.get()
            val preview = androidx.camera.core.Preview.Builder().build()
            val cameraSelector = androidx.camera.core.CameraSelector.Builder()
                .requireLensFacing(androidx.camera.core.CameraSelector.LENS_FACING_BACK)
                .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)
            cameraProvider.bindToLifecycle(
                context as androidx.lifecycle.LifecycleOwner,
                cameraSelector,
                preview
            )
            previewView

        }
    )
}